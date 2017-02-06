package service.api.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.person.Person;

public interface PersonManager
{

    Person savePerson( Person person );

    String generateCode();

    Person createPerson( String code, String firstName, String lastName, String middleName );

    Page<Person> getPersonListByFirstNameAndLastName( String firstName, String lastName, Pageable pageable );

}
