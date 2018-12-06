![logo](src/main/resources/static/images/logo-dark.png =80x80)
# Muzyczna Studnia
[![Build Status](https://travis-ci.org/mateodziej/muzyczna_studnia.svg?branch=1.6.8)](https://travis-ci.org/mateodziej/muzyczna_studnia)

Aplikacja podpowiadająca nadchodzące wydarzenia muzyczne z naszej okolicy na
podstawie ostatnio słuchanych przez nas zespołów, oraz inne wydarzenia zdefiniowane
przez nas na podstawie tagów, integrująca się z naszym kontem na last.fm,
pozwalająca śledzić status wybranych przez nas wydarzeń.

### Stos technologiczny
Front-end:
  - AngularJS
  - jQuery
  - SASS
  - Materialize
  - FontAwesome

Back-end:
  - Spring Boot
  - Spring Security
  - Google SSO OAuth 2
  - Spring Data JPA
  - Hibernate
  - PostgreSQL

### Narzędzia i Środowiska
  -  Java SE 1.8 -> https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
  - Travis CI -> https://travis-ci.org/mateodziej/muzyczna_studnia
  - Packagecloud -> https://packagecloud.io/mateodziej/muzyczna-studnia

### Zewnętrzne REST API:
  - https://www.last.fm/pl/api
  - https://developer.ticketmaster.com/products-and-docs/apis/getting-started/
  - https://cloud.google.com/maps-platform/

## Instrukcja instalacji

> Kolejne wersje aplikacji są budowane i wdrażane za pomocą Travis CI na platformę Packageclodud.

1. Należy pobrać aktualną wersję wchodząc pod adres:
```sh
https://packagecloud.io/mateodziej/muzyczna-studnia
```

2. Pobrany plik muzyczna_studnia-{wersja}.jar uruchamiamy w terminalu poleceniem:
```sh
java -jar muzyczna_studnia-{wersja}.jar
```

> Do poprawnego działania wymagane jest środowisko Java SE 1.8 -> https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

## Instrukcja użytkownika

Opis modułów graficznego interfejsu użytkownika aplikacji internetowej

### MUZYKA

> W zakładce "MUZYKA" wyświetlane są wszystkie znalezione eventy muzyczne dla danego
> miasta, na podstawie ostatnio słuchanych przez użytkownika artystów na portalu 
> last.fm oraz zdefiniowanych przez niego wykonawców.

### WYDARZENIA

> W zakładce "WYDARZENIA" prezentowane są pozostałe wydarzenia w danym mieście,
> zdefiniowane na podstawie tagów użytkownika.

### PROFIL

> Zakładka "PROFIL" pozwala na zarządzanie swoim profilem użytkownika,
> m.in. zmianę miasta wyszukiwania, automatyczne wyszukiwanie miasta na podstawie
> geolokalizacji klienta użytkownika, zarządzanie artystami, oraz tagami
użytkownika.
