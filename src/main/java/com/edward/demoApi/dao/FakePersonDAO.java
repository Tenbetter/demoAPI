package com.edward.demoApi.dao;

import com.edward.demoApi.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository annotation marks this out as a repository bean to be instantiated and injected into any class that needs it by spring
//@Component can be used also, but @Repository is more descriptive
@Repository("FakePersonDAO")
public class FakePersonDAO implements IPersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    public void deletePersonById(UUID id){
      Optional<Person> personOptional = selectPersonById(id);
      if(personOptional.isEmpty()){
         //throw error
      }
      DB.remove(personOptional.get());

    }

    @Override
    public void updatePerson(Person person) {
        List<Person> people = selectAllPeople();
        Optional<Person> personOptional = people.stream().filter(p->p.getId().equals(person.getId())).findFirst();
        if(personOptional.isEmpty()){
            //throw error
        }
        int index = DB.indexOf(personOptional.get());
        DB.set(index, person);

    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(p -> p.getId().equals(id)).findFirst();
    }

}
