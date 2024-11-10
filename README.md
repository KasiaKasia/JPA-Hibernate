# JPA-Hibernate
 
## Pojecia:

## OOP (Object-Oriented Programming)
Podejście do definiowania programów za pomocą obiektów odzwierciedlających rzeczywistość.

## ORM (Object-Relational Mapping)
W praktyce ORM pozwala na mapowanie struktur baz danych (np. tabel) na obiekty i odwrotnie.

OOP <=> ORM  <=> RDB
Object  Mapping  Tabela

## Encja (Entity)
to klasa w programowaniu obiektowym, która odwzorowuje tabelę w relacyjnej bazie danych. W kontekście ORM (Object-Relational Mapping), encja reprezentuje pojedynczy wiersz tabeli bazy danych jako obiekt w kodzie. Każdy obiekt encji jest instancją klasy, która zawiera pola odpowiadające kolumnom tej tabeli oraz dodatkowe adnotacje, które informują ORM o mapowaniu obiektu na strukturę bazy danych.

## JPA (Jakarta Persistence API)
 to specyfikacja Java, która definiuje standardy dotyczące mapowania obiektowo-relacyjnego (ORM) w aplikacjach Java. 
 JPA umożliwia programistom pracę z danymi w bazach danych za pomocą obiektów Java, bez konieczności pisania bezpośrednich zapytań SQL. 
 Jest to standard, który pozwala na bardziej eleganckie i abstrakcyjne zarządzanie danymi.
 
 
## Dostawca JPA
JPA jest specyfikacją, oznacza to, że żeby móc z niego korzystać, musimy mieć DOSTAWCE, który będzie taką specyfikację implementował np.: Hibernate.

## JPA składa się z 
zdjęcie


## Persistence Unit
Definiuje zestaw wszystkich klas encji, które mają być zarządzane przez EntityManager. Określa się go w pliku konfiguracyjnym `persistence.xml`
```
<persistence
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
	version="3.0" xmlns="https://jakarta.ee/xml/ns/persistence">

	<persistence-unit name="JPA"
		transaction-type="RESOURCE_LOCAL">
		<class>pl.kasia.Person</class>
		<properties>
			<property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver" />
			<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/db" />
			<property name="jakarta.persistence.jdbc.user" value="postgres" />
			<property name="jakarta.persistence.jdbc.password" value="postgres" />
			<property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect" />
			<property name="hibernate.hbm2ddl.auto" value="create-drop" />
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true" />
		</properties>
	</persistence-unit>
</persistence>
```

## Encja (ang. entity)
Jest to reprezentacja obiektu świata rzeczywistego, zawierająca informację o tych obiektach, które są przechowywane w bazie danych. 
Obiektami encji w praktyce są proste obiekty POJO, które odzwierciedlają tabele w relacyjnej bazie danych, gdzie pola obiektu odzwierciedlają kolumny z tabeli.
Mapowanie encji do tabeli definiuje się za pomocą adnotacji lub plików XML.

## Persistence
jakarta.persistence.Persistence Klasa, która zawiera statyczne metody pomagające uzyskać EntityManagerFactory w sposób niezależny od używanego dostawcy JPA.




## EntityManagerFactory
jakarta.persistence.EntityManagerFactory Interfejs, który służy do utworzenia instancji EntityManager, gdzie każda z instancji zarządza
połączeniem do tej samej bazy danych. EntityManagerFactory może być wiele, ale najczęściej definiuje
się jeden dedykowany dla każdego źródła danych w aplikacji. 

`EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("zajavkaPersistenceUnitExample");`

## EntityManager
jakarta.persistence.EntityManager Interfejsu tego używa się do wykonania jakiegoś zadania na bazie danych. W grę wchodzą operacje CRUD. 
Każda instancja EntityManager jest powiązana z Persistence Context. 
`EntityManager entityManager = entityManagerFactory.createEntityManager();`


## EntityTransaction
jakarta.persistence.EntityTransaction Ten interfejs umożliwia grupowanie wielu operacji na danych w jednej transakcji. 
Innymi słowy, zbiera wszystkie zmiany na powiązanych encjach i wprowadza je na bazie danych jako pojedyncza transakcja, zgodnie z zasadą: wszystko albo nic. 
 
`EntityTransaction transaction = entityManager.getTransaction();`

## Query
jakarta.persistence.Query Interfejs używany do wykonywania zapytań na bazie danych. Za dostarczenie Query odpowiada EntityManager.
``` 
String selectQuery = "SELECT p FROM Person p";
Query query = entityManager.createQuery(selectQuery);
List<Person> people = query.getResultList();
```

## Criteria API
Jest to zbiór klas wspierających budowanie zapytań SQL za pomocą obiektów Javowych, które pozwalają na dynamiczne tworzenie zapytań.
```
CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
Root<Person> root = criteriaQuery.from(Person.class);
criteriaQuery.select(root);
TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);
List<Person> people = query.getResultList();
```


## Adnotacje
Podczas pracy z JPA będziemy korzystali z adnotacji. Adnotacje takie będziemy dodawać w klasie Java,
która będzie nam określała encję w bazie danych.  

• Adnotacje definiujące klasę encji:
◦ @Entity - Identyfikuje klasę jako encję,
◦ @Table - Określa do jakiej tabeli odnosi klasa encji,

• Adnotacje określające mapowanie kolumn:
◦ @Column - Określa, na jaką kolumnę z tabeli mapowane jest pole klasy. Dodatkowo umożliwia
dostosowanie jej atrybutów jak np. updatable, czy insertable, określające czy kolumna jest
uwzględniania w generowanej instrukcji UPDATE lub INSERT,
◦ @Id - oznacza, które pole klasy jest kluczem głównym w tabeli,
◦ @GeneratedValue - Stosowana razem z adnotacją @Id do oznaczenia strategii generowania
wartości klucza głównego,
◦ @Enumerated - Pozwala wybrać czy mapowanie enum’a działa na podstawie nazwy enum’a
(EnumType.STRING), czy według jego kolejność (EnumType.ORDINAL)
◦ @Lob - Oznacza, że pole ma być traktowane jako duży obiekt w typie wspieranym przez bazę
danych,

• Mapowanie asocjacji:
◦ @ManyToMany - dla asocjacji wiele-do-wielu,
◦ @ManyToOne - dla asocjacji wiele-do-jednego,
◦ @OneToMany - dla asocjacji jeden-do-wielu,
◦ @OneToOne - dla asocjacji jeden-do-jednego,
◦ @JoinColumn - określa kolumnę, na podstawie której tworzona jest relacja między encjami.


## Cykl życia encji
• Transient (New) - Stan nowo utworzonej instancji obiektu encji, która nie jest jeszcze powiązana z
EntityManager’em i nie jest zapisana w bazie.
• Persistent (Managed) - Encja jest już we władaniu persistent context’u, co oznacza, że persistent
provider (np. Hibernate) pilnuje zmian w encji. Do bazy danych zmiany wprowadzane są przez
metodę persist() wywołaną przez EntityManager’a, co ważne, musi być to wykonane w obrębie
aktywnej transakcji. Jeżeli encja jest w stanie persistent, zmiany w encji są automatycznie
synchronizowane na bazie z momentem zatwierdzenia transakcji.
• Detached - Stan oznaczający, że encja nie jest już zarządzana przez EntityManager’a i nie ma
gwarancji na synchronizację encji z bazą danych.
• Removed (Deleted) - Użycie metody remove() z EntityManager ustawia encję w stan Removed, co
oznacza, że po zakończeniu transakcji, odpowiedni wiersz w bazie danych zostanie usunięty


## Wyjątki
Wyjątki używane przez JPA dziedziczą po RuntimeException i są unchecked:

• jakarta.persistence.PersistenceException
 jest to głowna klasa wyjątków JPA , która reprezentuję podstawowe wyjątki. 
Z niej dziedzicza poszczególne bardziej szczegółowe wyjątki.
◦ jakarta.persistence.TransactionRequiredException
Pojawia się, kiedy operacja jest wykonywana bez wymaganej aktywnej transakcji.
◦ jakarta.persistence.RollbackException
Rzucany w przypadku błędu podczas zatwierdzania transakcji (commit()), czyli podczas zapisu
zmian w bazie danych.
◦ jakarta.persistence.EntityExistsException
Rzucany w przypadku wprowadzania do bazy encji (persist()), która już istnieje.
◦ jakarta.persistence.EntityNotFoundException
Rzucany w przypadku braku szukanej encji.
◦ jakarta.persistence.QueryTimeoutException
Rzucany w przypadku zbyt długiego wykonywania zapytania.


