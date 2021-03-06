package org.scalawiki.wlx.dto

import org.specs2.mutable.Specification

class KoatuuSpec extends Specification {

  "Koatuu" should {

    val regions = Country.Ukraine.regions

    val topRegions = Map(
      "01" -> "Автономна Республіка Крим",
      "05" -> "Вінницька область",
      "07" -> "Волинська область",
      "12" -> "Дніпропетровська область",
      "14" -> "Донецька область",
      "18" -> "Житомирська область",
      "21" -> "Закарпатська область",
      "23" -> "Запорізька область",
      "26" -> "Івано-Франківська область",
      "32" -> "Київська область",
      "35" -> "Кіровоградська область",
      "44" -> "Луганська область",
      "46" -> "Львівська область",
      "48" -> "Миколаївська область",
      "51" -> "Одеська область",
      "53" -> "Полтавська область",
      "56" -> "Рівненська область",
      "59" -> "Сумська область",
      "61" -> "Тернопільська область",
      "65" -> "Херсонська область",
      "63" -> "Харківська область",
      "68" -> "Хмельницька область",
      "71" -> "Черкаська область",
      "73" -> "Чернівецька область",
      "74" -> "Чернігівська область",
      "80" -> "Київ",
      "85" -> "Севастополь"
    )

    "contain level1 names" in {
      regions.map(_.name).toSet === topRegions.toSeq.map(_._2).toSet
    }

    "lookup level1 by code" in {
      topRegions.toSet === Country.Ukraine.regionById.mapValues(_.name).toSet
    }

    "contain Kyiv raions" in {
      val kyiv = regions.find(_.name == "Київ").get
      kyiv.regions.map(_.name) === Seq("Райони м. Київ", "Голосіївський", "Дарницький", "Деснянський", "Дніпровський",
        "Оболонський", "Печерський", "Подільський", "Святошинський", "Солом'янський", "Шевченківський")
    }

    "find Kyiv raions by code" in {
      val kyiv = regions.find(_.name == "Київ").get
      kyiv.regionById.mapValues(_.name).toSet === Map(
        "80300" -> "Райони м. Київ",
        "80361" -> "Голосіївський",
        "80363" -> "Дарницький",
        "80364" -> "Деснянський",
        "80366" -> "Дніпровський",
        "80380" -> "Оболонський",
        "80382" -> "Печерський",
        "80385" -> "Подільський",
        "80386" -> "Святошинський",
        "80389" -> "Солом'янський",
        "80391" -> "Шевченківський",
      ).toSet
    }

    "contain Crimea regions" in {
      val crimea = regions.find(_.name == "Автономна Республіка Крим").get
      crimea.regions.map(_.name) === Seq(
        "Міста Автономної Республіки Крим",
        "Сімферополь", "Алушта", "Джанкой", "Євпаторія", "Керч",
        "Красноперекопськ", "Саки", "Армянськ", "Феодосія", "Судак", "Ялта",
        "Райони Автономної Республіки Крим",
        "Бахчисарайський район", "Білогірський район", "Джанкойський район", "Кіровський район", "Красногвардійський район",
        "Красноперекопський район", "Ленінський район", "Нижньогірський район", "Первомайський район", "Роздольненський район",
        "Сакський район", "Сімферопольський район", "Совєтський район", "Чорноморський район")
    }

    "contain Vinnytsya oblast regions" in {
      val crimea = regions.find(_.name == "Вінницька область").get
      crimea.regions.map(_.name) === Seq(
        "Міста обласного підпорядкування Вінницької області",
        "Вінниця", "Жмеринка", "Могилів-Подільський", "Козятин", "Ладижин", "Хмільник",
        "Райони Вінницької області",
        "Барський район", "Бершадський район", "Вінницький район", "Гайсинський район", "Жмеринський район",
        "Іллінецький район", "Козятинський район", "Калинівський район", "Крижопільський район", "Липовецький район",
        "Літинський район", "Могилів-Подільський район", "Мурованокуриловецький район", "Немирівський район",
        "Оратівський район", "Піщанський район", "Погребищенський район", "Теплицький район", "Томашпільський район",
        "Тростянецький район", "Тульчинський район", "Тиврівський район", "Хмільницький район", "Чернівецький район",
        "Чечельницький район", "Шаргородський район", "Ямпільський район")
    }

  }
}
