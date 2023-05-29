# Проект по автоматизации тестирования для компании [NOVO-DOM](https://novo-dom.ru/) — цифровое агентство недвижимости, которое предлагает услуги по продаже и подбору квартир в новостройках Москвы и Московской области. 
<img alt="GIPHY" src="design/pictures/novo.png">

# <a name="TableOfContents">Содержание страницы</a>
+ [Описаниe](#Description)
+ [:trophy: Инструменты и технологии](#ToolsAndTechnologies)
+ [:computer: Запуск локально](#Launch_from_terminal)
  - <a href="#console-ui"> UI
  - <a href="#console-api"> API
  - <a href="#console-remote"> Remote
+ [:clipboard: Параметры сборки в Jenkins:](#Build_Parameters_in_Jenkins)
+ [:clipboard: Сборка в Github:](#Github)
+ [:chart_with_downwards_trend: Allure отчет](#Allure_report)
  + [Главный экран отчета](#Allure_report1)
  + [Страница с проведенными тестами](#Allure_report2)
+ [:bar_chart: Интеграция с Allure TestOps](#Integration_Allure_TestOps)
+ [:iphone: Отчет в Telegram](#Telegram)
+ [:movie_camera: Видео пример прохождения тестов](#Video)
  
<a name="Description"><h2>Описаниe</h2></a>
Тестовый проект состоит из UI и API автотестов. Автотесты в этом проекте написаны на `Java` с использованием `Selenide`.\
Краткий список интересных фактов о проекте: \
`Page Object` проектирование  \
`Параметризованные тесты` \
Конфигурация с библиотекой `Owner` \
`Custom Allure listener` для API requests/responses логов \
`Gradle` - для автоматической системы сборки.  \
`Jenkins` - CI/CD для удаленного запуска тестов.\
`Selenoid` - для удаленного запуска браузеров в `Docker` контейнере.\
`Allure Report` - для визуализации результатов тестирования.\
`Telegram Bot` - для уведомлений о результатах тестирования.\
`Allure TestOps` - система управления тестированием
`Rest Assured` - выполняет роль обёртки над http клиентом

<a name="ToolsAndTechnologies"><h2>:trophy: Инструменты и технологии</h2></a>
<p  align="center">
  <a href="https://www.jetbrains.com/idea/"><code><img width="5%" title="IntelliJ IDEA" src="/design/icons/Intelij_IDEA.svg"></code></a>
  <a href="https://www.java.com/"><code><img width="5%" title="Java" src="/design/icons/Java.svg"></code></a>
  <a href="https://selenide.org/"></a><code><img width="5%" title="Selenide" src="/design/icons/Selenide.svg"></code></a>
  <a href="https://aerokube.com/selenoid/"><code><img width="5%" title="Selenoid" src="/design/icons/Selenoid.svg"></code></a>
  <a href="https://gradle.org/"><code><img width="5%" title="Gradle" src="/design/icons/Gradle.svg"></code></a>
  <a href="https://junit.org/junit5/"><code><img width="5%" title="JUnit5" src="/design/icons/JUnit5.svg"></code></a>
  <a href="https://docs.qameta.io/allure/"><code><img width="5%" title="Allure Report" src="/design/icons/Allure_Report.svg"></code></a>
  <a href="https://qameta.io/"><code><img width="5%" title="Allure TestOps" src="/design/icons/AllureTestOps.svg"></code></a>
  <a href="https://github.com/"><code><img width="5%" title="Github" src="/design/icons/GitHub.svg"></code></a>
  <a href="https://git-scm.com/"><code><img width="5%" title="Github" src="/design/icons/Git.svg"></code></a>
  <a href="https://www.jenkins.io/"><code><img width="5%" title="Jenkins" src="/design/icons/Jenkins.svg"></code></a>
  <a href="https://www.atlassian.com/ru/software/jira"><code><img width="5%" title="Jira" src="/design/icons/Jira.svg"></code></a>
  <a href="https://telegram.org/"><code><img width="5%" title="Telegram" src="/design/icons/Telegram.svg"></code></a>
  <a href="https://rest-assured.io/"><code><img width="5%" title="REST-Assured" src="/design/icons/rest-assured-logo.svg"></code></a>
</p>

<a name="Launch_from_terminal"><h2>:computer: Запуск локально</h2></a>
### <a id="console-ui"></a>Локальный запуск UI-тестов

```
gradle clean novoWeb
```

### <a id="console-api"></a>Локальный запуск API-тестов

```
gradle clean novoApi
```

### <a id="console-mobile"></a>Локальный запуск всех тестов

```
gradle clean test
```
### <a id="console-remote"></a>Запуск тестов на selenoid

```
gradle test -Denv=remote
```
![This is an image](design/pictures/ctk1.png)
![This is an image](design/pictures/ctk2.png)
![This is an image](design/pictures/ctk3.png)
  
<a name="Build_Parameters_in_Jenkins"><h2>:clipboard: Параметры сборки в Jenkins:</h2></a>

Сборка в Jenkins

- TASK (с какими тегами запускать тесты)

- BROWSER (браузер, по умолчанию chrome)

- BROWSER_VERSION (версия браузера, по умолчанию 100.0)

- BROWSER_SIZE (размер окна браузера, по умолчанию 2500х1080)

![This is an image](design/pictures/jenk_par.png)
![This is an image](design/pictures/jen_pr.png)
  
<a name="Github"><h2>:clipboard: Сборка в Github на странице Actions с формированием Allure report:</h2></a>
  
![This is an image](design/pictures/гит1.png)
![This is an image](design/pictures/гит2.png)
 ![This is an image](design/pictures/гит3.png)
  
<a name="Allure_report"><h2>:chart_with_downwards_trend: [Allure](https://jenkins.autotests.cloud/job/novo/) отчет</h2></a>

- <a name="Allure_report1"><h3>Главный экран отчета</h3></a>

![This is an image](design/pictures/allure_1.png)

-  <a name="Allure_report2"><h3>Страница с проведенными тестами</h3></a>

![This is an image](design/pictures/allure_2.png)

<a name="Integration_Allure_TestOps"><h2>:bar_chart: Интеграция с Allure [TestOps](https://allure.autotests.cloud/launch/24448)</h2></a>

- <a name="Integration_Allure_TestOps1"><h3>Экран с результатами запуска тестов</h3></a>

![This is an image](design/pictures/tes1.png)


- <a name="Integration_Allure_TestOps2"><h3>Страница с тестами в [TestOps](https://allure.autotests.cloud/launch/24091/tree?treeId=4235)</h3></a>

![This is an image](design/pictures/tes2.png)

<a name="Telegram"><h2>:iphone: Отчет в Telegram</h2></a>

![This is an image](design/pictures/tel.png)
  
<a name="Video"><h2>:movie_camera: Видео пример прохождения тестов</h2></a>

https://github.com/PrudnikovaEkaterina/Novo/blob/master/design/video/499795bd7c887581758586676ca83ff3%20-%20Trim.mp4
