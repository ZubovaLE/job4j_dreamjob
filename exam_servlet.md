1. [Что такое сервлет?](#1-Что-такое-сервлет)
2. [Какова структура веб-проекта?](#2-Какова-структура-веб-проекта)
3. [Что такое контейнер сервлетов?](#3-Что-такое-контейнер-сервлетов)
4. [Каковы задачи, функциональность контейнера сервлетов?](#4-Каковы-задачи,-функциональность-контейнера-сервлетов)
5. [Что вы знаете о сервлет фильтрах?](#5-Что-вы-знаете-о-сервлет-фильтрах)
6. [Зачем нужны слушатели в сервлетах?](#6-Зачем-нужны-слушатели-в-сервлетах)
7. Когда вы будете использовать фильтры, а когда слушатели?
8. Как обработать исключения, выброшенные другим сервлетом в приложении?
9. Что такое дескриптор развертывания?
10. Как реализовать запуск сервлета с запуском приложения?
11. Что представляет собой объект ServletConfig?
12. Что представляет собой объект ServletContext?
13. В чем отличия ServletContext и ServletConfig?
14. Что такое Request Dispatcher?
15. Как можно создать блокировку (deadlock) в сервлете?
16. Как получить адрес сервлета на сервере?
17. Как получить информацию о сервере из сервлета?
18. Как получить ip адрес клиента на сервере?
19. Что вы знаете о классах обертках (wrapper) для сервлетов?
20. Каков жизненный цикл сервлета и когда какие методы вызываются?
21. Какие методы необходимо определить при создании сервлетов?
22. В каком случае вы будете переопределять метод service()?
23. Есть ли смысл определить конструктор для сервлета, как лучше инициализировать данные?
24. В чем отличия GenericServlet и HttpServlet?
25. Как вызвать из сервлета другой сервлет этого же и другого приложения?
26. Что вы знаете и в чем отличия методов forward() и sendRedirect()?
27. Стоит ли волноваться о “многопоточной безопасности” работая с сервлетами?
28. В чем отличие между веб сервером и сервером приложений?
29. Какой метод HTTP не является неизменяемым?
30. Почему HttpServlet класс объявлен как абстрактный?
31. В чем разница между методами GET и POST?
32. Что такое MIME-тип?
33. Назовите преимущества Servlet над CGI?
34. Каковы наиболее распространенные задачи выполняются в Servlet контейнере?
35. В чем разница между PrintWriter и ServletOutputStream?
36. [Можем ли мы получить PrintWriter и ServletOutputStream одновременно в сервлете?](#36-Можем-ли-мы-получить-PrintWriter-и-ServletOutputStream-одновременно-в-сервлете)
37. Расскажите о интерфейсе SingleThreadModel.
38. [Какие существуют атрибуты у сервлетов и какая сфера их применения?](#38-Какие-существуют-атрибуты-у-сервлетов-и-какая-сфера-их-применения)
39. Почему необходимо переопределить только init() метод без аргументов?
40. Что означает URL encoding? Зачем нужны методы java.net.URLEncoder.encode() и decode()?
41. Зачем нужны и чем отличаются методы encodeUrl() и encodeRedirectUrl()?
42. Какие различные методы управления сессией в сервлетах вы знаете?
43. [Что означает URL Rewriting?](#43-Что-означает-URL-Rewriting)
44. [Как применяются Cookies в сервлетах?](#45-Как-применяются-Cookies-в-сервлетах)
45. [Как уведомить объект в сессии, что сессия недействительна или закончилась?](#45-Как-уведомить-объект-в-сессии-что-сессия-недействительна-или-закончилась)
46. [Какой существует эффективный способ удостоверится, что все сервлеты доступны только для пользователя с валидной сессией?](#46-Какой-существует-эффективный-способ-удостоверится-что-все-сервлеты-доступны-только-для-пользователя-с-валидной-сессией)
47. [Как мы можем обеспечить transport layer security для нашего веб приложения?](#47-Как-мы-можем-обеспечить-transport-layer-security-для-нашего-веб-приложения)
48. Как организовать подключение к базе дынных и обеспечить логирование log4j в сервлете?
49. Какие важные особенности существуют в Servlet 3?
50. Каковы различные способы аутентификации сервлета?
51. Написать сервлет, реализующий загрузку файла на сервер.

## 1 Что такое сервлет?

Serlvet - это интерфейс, при реализации которого расширяются функциональные возможности сервера. Serlvet позволяет
обработать запрос клиента и отправить ему ответ. Обычно сервлеты используются в веб-приложениях. Для таких приложений
технология Java Servlet определяет HTTP-специфичные сервлет-классы. Пакеты javax.servlet и javax.servlet.http
обеспечивают интерфейсы и классы для создания сервлетов.

## 2 Какова структура веб-проекта?

## 3 Что такое контейнер сервлетов?

Контейнер сервлетов — программа, представляющая собой сервер, который занимается системной поддержкой сервлетов и
обеспечивает их жизненный цикл в соответствии с правилами, определёнными в спецификациях.

Известные реализации:Apache Tomcat, Jetty, JBoss, GlassFish, IBM WebSphere, Oracle Weblogic

## 4 Каковы задачи, функциональность контейнера сервлетов?

Контейнер сервлетов может работать как полноценный самостоятельный веб-сервер, быть поставщиком страниц для другого
веб-сервера, например Apache, или интегрироваться в Java EE сервер приложений. Обеспечивает обмен данными между
сервлетом и клиентами, берёт на себя выполнение таких функций, как создание программной среды для функционирующего
сервлета, идентификацию и авторизацию клиентов, организацию сессии для каждого из них.

## 5 Что вы знаете о сервлет фильтрах?

## 6 Зачем нужны слушатели в сервлетах?

Слушатели контекста и сессий — это классы, которые могут следить за тем, когда контекст или сессия были
инициализированы, или отслеживать время, когда они должны быть уничтожены, и когда атрибуты были добавлены или удалены
из контекста или сессии. Servlet 2.4 расширяет модель слушателей запроса, позволяя отслеживать, как запрос создается и
уничтожается, и, как атрибуты добавляются и удаляются из сервлета. В Servlet 2.4 добавлены следующие классы:

ServletRequestListener ServletRequestEvent ServletRequestAttributeListener ServletRequestAttributeEvent

## 7 Когда вы будете использовать фильтры, а когда слушатели?

## 8 Как обработать исключения, выброшенные другим сервлетом в приложении?

## 9 Что такое дескриптор развертывания?

## 10 Как реализовать запуск сервлета с запуском приложения?

## 11 Что представляет собой объект ServletConfig?

Интерфейс javax.servlet.ServletConfig используется для передачи конфигурационной информации сервлету. Каждый сервлет
имеет свой собственный объект ServletConfig, за создание экземпляра которого ответственен контейнер сервлетов. Для
установки параметров конфигурации используются init параметры в web.xml (или аннотации WebInitParam). Для получения
объекта ServletConfig данного сервлета используется метод getServletConfig().

12. Что представляет собой объект ServletContext?
13. В чем отличия ServletContext и ServletConfig?
14. Что такое Request Dispatcher?
15. Как можно создать блокировку (deadlock) в сервлете?
16. Как получить адрес сервлета на сервере?
17. Как получить информацию о сервере из сервлета?
18. Как получить ip адрес клиента на сервере?
19. Что вы знаете о классах обертках (wrapper) для сервлетов?
20. Каков жизненный цикл сервлета и когда какие методы вызываются?
21. Какие методы необходимо определить при создании сервлетов?
22. В каком случае вы будете переопределять метод service()?
23. Есть ли смысл определить конструктор для сервлета, как лучше инициализировать данные?
24. В чем отличия GenericServlet и HttpServlet?
25. Как вызвать из сервлета другой сервлет этого же и другого приложения?
26. Что вы знаете и в чем отличия методов forward() и sendRedirect()?
27. Стоит ли волноваться о “многопоточной безопасности” работая с сервлетами?
28. В чем отличие между веб сервером и сервером приложений?
29. Какой метод HTTP не является неизменяемым?
30. Почему HttpServlet класс объявлен как абстрактный?
31. В чем разница между методами GET и POST?
32. Что такое MIME-тип?
33. Назовите преимущества Servlet над CGI?
34. Каковы наиболее распространенные задачи выполняются в Servlet контейнере?
35. В чем разница между PrintWriter и ServletOutputStream?

## 36 Можем ли мы получить PrintWriter и ServletOutputStream одновременно в сервлете?

Мы не можем создать два объекта этих классов в одном сервлете. При попытке внедрить оба метода getWriter() и
getOutputStream() в ответе, мы получим исключение java.lang.IllegalStateException с сообщением, что уже другой метод был
вызван для этого ответа.

37. Расскажите о интерфейсе SingleThreadModel.

## 38 Какие существуют атрибуты у сервлетов и какая сфера их применения?

Атрибуты сервлетов ипользуются для внутренней коммуникации сервлетов. Мы можем использовать атрибуты set, get, remove в
веб-приложении. Существует три области видимости атрибутов — request scope, session scope, application scope.

Интерфейсы ServletRequest, HttpSession и ServletContext предоставляют методы для get(), set(), remove() атрибутов из
request scope, session scope, application scope соответственно.

39. Почему необходимо переопределить только init() метод без аргументов?

## 40 Что означает URL encoding? Зачем нужны методы java.net.URLEncoder.encode() и decode()?

URL Encoding — процесс преобразования данных в форму CGI (Common Gateway Interface), который позволит путешествовать по
сети без проблем. URL Encoding разделяет пробелы и заменяет специальные символы с помощью escape-симолов. Например, для
кодирования строки используется метод java.net.URLEncoder.encode(String str, String unicode). Обратная операция
декодирования возможна благодаря методу java.net.URLDecoder.decode(String str, String unicode). Пример работы метода:
строка «Java for study .ru» будет преобразована в Java%20for%20study%20.ru.

## 41 Зачем нужны и чем отличаются методы encodeUrl() и encodeRedirectUrl()?

## 42 Какие различные методы управления сессией в сервлетах вы знаете?

## 43 Что означает URL Rewriting?

Для управления сессией в сервлетах мы можем использовать HTTPSession, но он работает с Cookies, а их иногда отключают.
Для этого случая в сервлетах предусмотрена возможность URL Rewriting. С точки зрения программирования необходимо всего
одно действие — кодирование URL. Другим достоинством является то, что этот метод является как бы запасным и включается
только при выключенных куках.

Применяя метод HttpServletResponse encodeURL() мы можем закодировать URL. Если необходим редирект к другому ресурсу, то
для предоставления информации о сессии применяется метод encodeRedirectURL().

## 44 Как применяются Cookies в сервлетах?

Для работы с Cookies сервлеты могут использовать класс javax.servlet.http.Cookie. Для создания Cookie надо создать
объект этого класса с помощью конструктора Cookie(String name, String value), где *name* - ключ, а *value* - значение,
которое сохраняется в Cookies. Стоит отметить, что в Cookies можно сохранить только строки.

Чтобы добавить Cookie в ответ клиенту, у объекта HttpServletResponse применяется метод **addCookie(Cookie c)**, однако
не существует геттера для этого типа передачи данных.

Для получения массива cookies из запроса необходимо воспользоваться методом HttpServletRequest getCookies(). Для
добавления cookies в запрос методов не предусмотрено.

## 45 Как уведомить объект в сессии, что сессия недействительна или закончилась?

Чтобы быть уверенным об оповещении объекта о прекращении сессии, объект должен реализовывать интерфейс
javax.servlet.http.HttpSessionBindingListener. Два метода этого интерфейса: **valueBound()** и **valueUnbound()**
применяются для реализации логики при добавлении объекта в качестве атрибута к сессии и при уничтожении сессии.

## 46 Какой существует эффективный способ удостоверится, что все сервлеты доступны только для пользователя с валидной сессией?

Сервлет-фильтры используются для перехвата всех запросов между контейнером сервлетов и сервлетом. Поэтому логично
использовать фильтр для проверки необходимой информации (например, валидности сессии) в запросе.

## 47 Как мы можем обеспечить transport layer security для нашего веб приложения?

Для этого необходимо настроить SSL для вашего сервлет-контейнера. Подробнее о настройке SSL описано в мануалах для
конкретной реализации контейнера.

48. Как организовать подключение к базе дынных и обеспечить логирование log4j в сервлете?
49. Какие важные особенности существуют в Servlet 3?
50. Каковы различные способы аутентификации сервлета?
51. Написать сервлет, реализующий загрузку файла на сервер.