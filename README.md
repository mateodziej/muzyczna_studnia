# Muzyczna Studnia

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
  - Spring Data JPA
  - Hibernate
  - PostgreSQL

Zewnętrzne REST API:
  - https://www.last.fm/pl/api
  - https://developer.ticketmaster.com/products-and-docs/apis/getting-started/
  - https://cloud.google.com/maps-platform/

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
