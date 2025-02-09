package com.eazybytes.securityDemo;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class SampleTests {
    @Test
    public void treeTest(){

        Set<Person> personSet = new TreeSet<Person>();
        Person person1=new Person(1,1,"daniel1");
        Person person2=new Person(2,2,"daniel2");
        Person person3=new Person(3,3,"daniel3");
        Person person4=new Person(4,4,"daniel4");
        Person person5=new Person(5,4,"daniel5");
        personSet=new TreeSet<>(Set.of(person1,person2,person3,person4,person5));
        System.out.printf("set size "+ personSet.size());
    }

    public class Person implements Comparable<Person>{
        private int id;
        private int pos;
        private String name;

        public Person(int id, int pos, String name) {
            this.id = id;
            this.pos = pos;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return id == person.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public int compareTo(Person o) {
            return Integer.compare(this.pos,o.pos);
        }
    }
}
