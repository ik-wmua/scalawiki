package org.scalawiki.wlx.query

import org.scalawiki.dto.Namespace
import org.scalawiki.wlx.dto.{Contest, Monument}
import org.scalawiki.WithBot

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, _}

trait MonumentQuery {
  import scala.concurrent.duration._

  def contest: Contest

  def byMonumentTemplateAsync(template: String = contest.uploadConfigs.head.listTemplate): Future[Seq[Monument]]
  def byPageAsync(page: String, template: String, pageIsTemplate: Boolean = false): Future[Seq[Monument]]

  final def byMonumentTemplate(template: String = contest.uploadConfigs.head.listTemplate) = Await.result(byMonumentTemplateAsync(template), 15.minutes): Seq[Monument]
  final def byPage(page: String, template: String, pageIsTemplate: Boolean = false) = Await.result(byPageAsync(page, template, pageIsTemplate), 15.minutes): Seq[Monument]
}

class MonumentQueryApi(val contest: Contest) extends MonumentQuery with WithBot {

  val host = getHost

  val listConfig = contest.uploadConfigs.head.listConfig

  def getHost = {
    val langCode = contest.country.languageCode

    if (langCode.contains("."))
      langCode
    else
      langCode + ".wikipedia.org"
  }

  override def byMonumentTemplateAsync(template: String): Future[Seq[Monument]] = {
    bot.page("Template:" + template).revisionsByGenerator("embeddedin", "ei", Set(Namespace.PROJECT_NAMESPACE, Namespace.MAIN), Set("content", "timestamp", "user", "comment"), None, "100") map {
      pages =>
        pages.flatMap(page =>
          Monument.monumentsFromText(page.text.getOrElse(""), page.title, template, listConfig))
    }
  }

  override def byPageAsync(page: String, template: String, pageIsTemplate: Boolean = false): Future[Seq[Monument]] = {
    if (!page.startsWith("Template") || pageIsTemplate) {
      bot.page(page).revisions(Set.empty, Set("content", "timestamp", "user", "comment")).map {
        revs =>
          revs.headOption.map(page =>
            Monument.monumentsFromText(page.text.getOrElse(""), page.title, template, listConfig).toSeq).getOrElse(Seq.empty)
      }
    } else {
//      bot.page(page).revisionsByGenerator("links", null, Set.empty, Set("content", "timestamp", "user", "comment")).map {
//        pages =>
//          pages.flatMap(page => Monument.monumentsFromText(page.text.getOrElse(""), page.title, template).toSeq)
//      }
      bot.page(page).revisionsByGenerator("embeddedin", "ei", Set(Namespace.PROJECT_NAMESPACE), Set("content", "timestamp", "user", "comment"), None, "100") map {
        pages =>
          pages.flatMap(page => Monument.monumentsFromText(page.text.getOrElse(""), page.title, template, listConfig))
      }

    }
  }
}


class MonumentQuerySeq(val contest: Contest, monuments: Seq[Monument]) extends MonumentQuery {

  override def byMonumentTemplateAsync(template: String): Future[Seq[Monument]] = future { monuments }

  override def byPageAsync(page: String, template: String, pageIsTemplate: Boolean = false): Future[Seq[Monument]] = future { monuments }
}

class MonumentQueryCached(underlying: MonumentQuery) extends MonumentQuery {

  override def contest = underlying.contest

  import spray.caching.{Cache, LruCache}

  val cache: Cache[Seq[Monument]] = LruCache()

  override def byMonumentTemplateAsync(template: String): Future[Seq[Monument]] = cache(template) {
    underlying.byMonumentTemplateAsync(template)
  }

  override def byPageAsync(page: String, template: String, pageIsTemplate: Boolean = false): Future[Seq[Monument]] = cache(page) {
    underlying.byPageAsync(page, template: String)
  }
}


//class MonumentQueryPickling(underlying: MonumentQuery) extends MonumentQuery {
//
//  import scala.pickling._
////  import scala.pickling.json._   // :( Slow parsing
//  import java.nio.file.Files
//
//import scala.pickling.binary._  // :( exception with unpickling
//
//  override def contest = underlying.contest
//
//  override def byMonumentTemplateAsync(template: String): Future[Seq[Monument]]
//  = future {
//      val file = new File(s"cache/monuments_${contest.contestType.code}_${contest.country.code}_${contest.year}.bin")
//      if (file.exists()) {
//        val data = Files.readAllBytes(file.toPath)
//        data.unpickle[Seq[Monument]]
//      } else {
//        val list = underlying.byMonumentTemplate(template)
//        val data = list.pickle.value
//        Files.write(file.toPath, data)
//        list
//      }
//  }
//
//  override def byPageAsync(page: String, template: String, pageIsTemplate: Boolean = false): Future[Seq[Monument]] = ???
//}

object MonumentQuery {

  def create(contest: Contest, caching: Boolean = true, pickling: Boolean = false):MonumentQuery = {
    val api = new MonumentQueryApi(contest)

    val query = if (caching)
      new MonumentQueryCached(
//        if (pickling)
//          new MonumentQueryPickling(api)
//        else
          api
      )
    else api

    query
  }

}

