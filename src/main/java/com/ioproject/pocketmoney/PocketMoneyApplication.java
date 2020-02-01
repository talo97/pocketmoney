package com.ioproject.pocketmoney;

import com.ioproject.pocketmoney.entities.*;
import com.ioproject.pocketmoney.service.*;
import com.ioproject.pocketmoney.service.impl.ServiceAdministrationUnitImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//to add first records to database implement CommandLineRunner interface and uncomment function run()
@SpringBootApplication
public class PocketMoneyApplication{

    public static void main(String[] args) {
        SpringApplication.run(PocketMoneyApplication.class, args);
    }


//    //uncomment if you want to add default values
//    @Autowired
//    ServiceAdministrationUnit serviceAdministrationUnit;
//    @Autowired
//    ServiceEducation serviceEducation;
//    @Autowired
//    ServiceGroup serviceGroup;
//    @Autowired
//    ServiceUser serviceUser;
//    @Autowired
//    ServiceChild serviceChild;
//
//    /**
//     * add all default records to database
//     *
//     * @param args
//     * @throws Exception
//     */
//    @Override
//    public void run(String... args) throws Exception {
//        //group
//        EntityGroup group = new EntityGroup("DEFAULT");
//        serviceGroup.save(group);
//
//        //user
//        EntityUser user = new EntityUser("vaniat", "123", "arturo@ara.ara", "Artur", "Talik", group);
//        serviceUser.save(user);
//        user = new EntityUser("charizard", "123", "charizard@ara.ara", "Czarek", "Muszynski", group);
//        serviceUser.save(user);
//        user = new EntityUser("dawur", "123", "powaznymail@ara.ara", "Dawid", "Pawliczek", group);
//        serviceUser.save(user);
//        user = new EntityUser("IMPIOUS_DEVIOUS_LEPER_LORD", "123", "JOJO@ara.ara", "Bartosz", "Kawalkiewicz", group);
//        serviceUser.save(user);
//        user = new EntityUser("MagicTurtle", "123", "SMIW@ara.ara", "Pawel", "Zaton", group);
//        serviceUser.save(user);
//        user = new EntityUser("Nieznany", "123", "AHAHAHAH@ara.ara", "Jakub", "Sobolewski", group);
//        serviceUser.save(user);
//        group = new EntityGroup("ADMIN");
//        serviceGroup.save(group);
//        user = new EntityUser("XD", "123", "Ricardo@Milos.meme", "Duzo", "Dzieci", group);
//        serviceUser.save(user);
//
//        //adm unit
//        List<EntityAdministrationUnit> admUnits = new ArrayList<>();
//        admUnits.add(new EntityAdministrationUnit("dolnoslaskie", null)); //0
//        admUnits.add(new EntityAdministrationUnit("kujawsko-pomorskie", null));//1
//        admUnits.add(new EntityAdministrationUnit("lubelskie", null));//2
//        admUnits.add(new EntityAdministrationUnit("lubuskie", null));//3
//        admUnits.add(new EntityAdministrationUnit("lodzkie", null));//4
//        admUnits.add(new EntityAdministrationUnit("malopolskie", null));//5
//        admUnits.add(new EntityAdministrationUnit("mazowieckie", null));//6
//        admUnits.add(new EntityAdministrationUnit("opolskie", null));//7
//        admUnits.add(new EntityAdministrationUnit("podkarpackie", null));//8
//        admUnits.add(new EntityAdministrationUnit("podlaskie", null));//9
//        admUnits.add(new EntityAdministrationUnit("pomorskie", null));//10
//        admUnits.add(new EntityAdministrationUnit("slaskie", null));//11
//        admUnits.add(new EntityAdministrationUnit("swietokrzyskie", null));//12
//        admUnits.add(new EntityAdministrationUnit("warminsko-mazurskie", null));//13
//        admUnits.add(new EntityAdministrationUnit("wielkopolskie", null));//14
//        admUnits.add(new EntityAdministrationUnit("zachodniopomorskie", null));//15
//        //big cities
//        admUnits.add(new EntityAdministrationUnit("Warszawa", admUnits.get(6)));
//        admUnits.add(new EntityAdministrationUnit("Krakow", admUnits.get(5)));
//        admUnits.add(new EntityAdministrationUnit("Lodz", admUnits.get(4)));
//        admUnits.add(new EntityAdministrationUnit("Wroclaw", admUnits.get(0)));
//        admUnits.add(new EntityAdministrationUnit("Poznan", admUnits.get(14)));
//        admUnits.add(new EntityAdministrationUnit("Gdansk", admUnits.get(10)));
//        admUnits.add(new EntityAdministrationUnit("Szczecin", admUnits.get(4)));
//        admUnits.add(new EntityAdministrationUnit("Bydgoszcz", admUnits.get(1)));
//        admUnits.add(new EntityAdministrationUnit("Lublin", admUnits.get(2)));
//        admUnits.add(new EntityAdministrationUnit("Katowice", admUnits.get(11)));
//        admUnits.add(new EntityAdministrationUnit("Rybnik", admUnits.get(11)));
//        admUnits.add(new EntityAdministrationUnit("Gliwice", admUnits.get(11)));
//        admUnits.forEach(e -> serviceAdministrationUnit.save(e));
//
//        //edu
//        List<EntityEducation> educations = new ArrayList<>();
//        educations.add(new EntityEducation("Podstawowka"));
//        educations.add(new EntityEducation("Gimnazjum"));
//        educations.add(new EntityEducation("Liceum"));
//        educations.add(new EntityEducation("Technikum"));
//        educations.add(new EntityEducation("Zawodowka"));
//        educations.add(new EntityEducation("Studia"));
//        educations.forEach(entityEducation -> serviceEducation.save(entityEducation));
//        //child
//        //current user - "XD" add a lot of children to this record
//        Random rand = new Random();
//        int boundEducation = 6; //this many school types
//        int boundAdmUnit = 28; //this many province/big cities records
//        int boundPocketMoney = 100;
//        for (int i = 0; i < 200; i++) {
//            EntityChild child = new EntityChild((float) rand.nextInt(boundPocketMoney), "", "male",
//                    true, admUnits.get(rand.nextInt(boundAdmUnit)), user,
//                    educations.get(rand.nextInt(boundEducation)));
//            serviceChild.save(child);
//        }
//        for (int i = 0; i < 200; i++) {
//            EntityChild child = new EntityChild((float) rand.nextInt(boundPocketMoney), "", "female",
//                    true, admUnits.get(rand.nextInt(boundAdmUnit)), user,
//                    educations.get(rand.nextInt(boundEducation)));
//            serviceChild.save(child);
//        }
//        for (int i = 0; i < 5; i++) {
//            EntityChild child = new EntityChild(550f, "tuturu Ohayo!", "male",
//                    false, admUnits.get(rand.nextInt(boundAdmUnit)), user,
//                    educations.get(rand.nextInt(boundEducation)));
//            serviceChild.save(child);
//        }
//        for (int i = 0; i < 15; i++) {
//            EntityChild child = new EntityChild(1150f, "ara ara", "male",
//                    true, admUnits.get(rand.nextInt(boundAdmUnit)), user,
//                    educations.get(rand.nextInt(boundEducation)));
//            serviceChild.save(child);
//        }
//
//        //add for one kid for each user
//        EntityChild childoooo = new EntityChild(10f, "nofajnie", "male",
//                false, admUnits.get(11), serviceUser.getByUsername("vaniat").get(), educations.get(1));
//        serviceChild.save(childoooo);
//        childoooo = new EntityChild(10f, "nofajnie", "male",
//                false, admUnits.get(11), serviceUser.getByUsername("charizard").get(), educations.get(1));
//        serviceChild.save(childoooo);
//        childoooo = new EntityChild(10f, "nofajnie", "male",
//                false, admUnits.get(11), serviceUser.getByUsername("dawur").get(), educations.get(1));
//        serviceChild.save(childoooo);
//        childoooo = new EntityChild(10f, "nofajnie", "male",
//                false, admUnits.get(11), serviceUser.getByUsername("IMPIOUS_DEVIOUS_LEPER_LORD").get(), educations.get(1));
//        serviceChild.save(childoooo);
//        childoooo = new EntityChild(10f, "nofajnie", "male",
//                false, admUnits.get(11), serviceUser.getByUsername("MagicTurtle").get(), educations.get(1));
//        serviceChild.save(childoooo);
//        childoooo = new EntityChild(10f, "nofajnie", "male",
//                false, admUnits.get(11), serviceUser.getByUsername("Nieznany").get(), educations.get(1));
//        serviceChild.save(childoooo);
//    }

    //TODO::
    //WEB security (w miare zrobione, testy itp.)
    //ogarnac REST z foreign key - rozwiazana za pomoca Data Access Object (DTO)
    //kontrolki do child wszystkie do zrobienia
    //przygotowac gotowca do mysql (jakis skrypcik zeby zapelnic adm_unit/school/groups)
    //w sumie api bedzie zarzadzac tylko user i child wiec spoko xd
}
