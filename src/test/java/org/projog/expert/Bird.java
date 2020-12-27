package org.projog.expert;

import static org.projog.expert.Facts.NONE_OF_THE_ABOVE;
import static org.projog.expert.Facts.cheek;
import static org.projog.expert.Facts.color;
import static org.projog.expert.Facts.eats;
import static org.projog.expert.Facts.family_albatross;
import static org.projog.expert.Facts.family_duck;
import static org.projog.expert.Facts.family_falcon;
import static org.projog.expert.Facts.family_flycatcher;
import static org.projog.expert.Facts.family_goose;
import static org.projog.expert.Facts.family_swallow;
import static org.projog.expert.Facts.family_swan;
import static org.projog.expert.Facts.family_vulture;
import static org.projog.expert.Facts.feet;
import static org.projog.expert.Facts.flight;
import static org.projog.expert.Facts.flight_profile;
import static org.projog.expert.Facts.head;
import static org.projog.expert.Facts.nostrils;
import static org.projog.expert.Facts.order_tubenose;
import static org.projog.expert.Facts.province;
import static org.projog.expert.Facts.season;
import static org.projog.expert.Facts.size;
import static org.projog.expert.Facts.state;
import static org.projog.expert.Facts.tail;
import static org.projog.expert.Facts.throat;
import static org.projog.expert.Facts.voice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/** Used by unit-tests to provide test-data required to classification of all of the species of birds. */
@SuppressWarnings("unchecked")
enum Bird {
   laysan_albatross(family_albatross(), color("white")),
   black_footed_albatross(family_albatross(), color("dark")),
   fulmar(order_tubenose(), size("medium"), flight("flap glide")),
   whistling_swan(family_swan(), voice("muffled musical whistle")),
   trumpeter_swan(family_swan(), voice("loud trumpeting")),
   canada_goose_winter("canada goose", family_goose(), season("winter"), state("vermont"), head("black"), cheek("white")),
   canada_goose_summer("canada goose", family_goose(), season("summer"), province("quebec"), head("black"), cheek("white")),
   snow_goose(family_goose(), color("white")),
   mallard_male("mallard", family_duck(), voice("quack"), head("green")),
   mallard_female("mallard", family_duck(), voice("quack"), color("mottled brown")),
   pintail(family_duck(), voice("short whistle")),
   turkey_vulture(family_vulture(), flight_profile("v shaped")),
   california_condor(family_vulture(), flight_profile("flat")),
   sparrow_hawk(family_falcon(), eats("insects")),
   peregrine_falcon(family_falcon(), eats("birds")),
   great_crested_flycatcher(family_flycatcher(), tail("long rusty")),
   ash_throated_flycatcher(family_flycatcher(), throat("white")),
   barn_swallow(family_swallow(), tail("forked")),
   cliff_swallow(family_swallow(), tail("square")),
   purple_martin(family_swallow(), color("dark")),
   cannot_identify((String) null, nostrils(NONE_OF_THE_ABOVE), feet(NONE_OF_THE_ABOVE));

   private final Optional<String> name;
   private Map<String, String> facts;

   Bird(List<Fact>... facts) {
      this.name = Optional.of(toString().replace('_', ' '));
      this.facts = toMap(facts);
   }

   Bird(String name, List<Fact>... facts) {
      this.name = Optional.ofNullable(name);
      this.facts = toMap(facts);
   }

   Optional<String> getName() {
      return name;
   }

   /** key = attribute, value = answer */
   Map<String, String> getFacts() {
      return new HashMap<>(facts);
   }

   private static Map<String, String> toMap(List<Fact>... facts) {
      return Arrays.stream(facts).flatMap(List::stream).collect(Collectors.toMap(Fact::getAttribute, Fact::getAnswer));
   }
}
