package org.projog.expert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Facts {
   /** An answer that is always available for when none of the other answers are appropriate. */
   static final String NONE_OF_THE_ABOVE = "none of the above";

   private Facts() {
   }

   static List<Fact> family_albatross() {
      return flatten(order_tubenose(), size("large"), wings("long narrow"));
   }

   static List<Fact> family_swan() {
      return flatten(order_waterfowl(), neck("long"), color("white"), flight("ponderous"));
   }

   static List<Fact> family_goose() {
      return flatten(order_waterfowl(), size("plump"), flight("powerful"));
   }

   static List<Fact> family_duck() {
      return flatten(order_waterfowl(), feed("on water surface"), flight("agile"));
   }

   static List<Fact> family_vulture() {
      return flatten(order_falconiforms(), eats("meat"), feed("scavange"), wings("broad"));
   }

   static List<Fact> family_falcon() {
      return flatten(order_falconiforms(), wings("long pointed"), head("large"), tail("narrow at tip"));
   }

   static List<Fact> family_flycatcher() {
      return flatten(order_passerformes(), bill("flat"), eats("flying insects"));
   }

   static List<Fact> family_swallow() {
      return flatten(order_passerformes(), wings("long pointed"), bill("short"));
   }

   static List<Fact> order_tubenose() {
      return flatten(nostrils("external tubular"), live("at sea"), bill("hooked"));
   }

   static List<Fact> order_waterfowl() {
      return flatten(feet("webbed"), bill("flat"));
   }

   static List<Fact> order_falconiforms() {
      return flatten(feet("curved talons"), bill("sharp hooked"));
   }

   static List<Fact> order_passerformes() {
      return feet("one long backward toe");
   }

   static List<Fact> nostrils(String answer) {
      return singleFact("nostrils", answer);
   }

   static List<Fact> live(String answer) {
      return singleFact("live", answer);
   }

   static List<Fact> bill(String answer) {
      return singleFact("bill", answer);
   }

   static List<Fact> size(String answer) {
      return singleFact("size", answer);
   }

   static List<Fact> eats(String answer) {
      return singleFact("eats", answer);
   }

   static List<Fact> feet(String answer) {
      return singleFact("feet", answer);
   }

   static List<Fact> wings(String answer) {
      return singleFact("wings", answer);
   }

   static List<Fact> neck(String answer) {
      return singleFact("neck", answer);
   }

   static List<Fact> color(String answer) {
      return singleFact("color", answer);
   }

   static List<Fact> flight(String answer) {
      return singleFact("flight", answer);
   }

   static List<Fact> feed(String answer) {
      return singleFact("feed", answer);
   }

   static List<Fact> head(String answer) {
      return singleFact("head", answer);
   }

   static List<Fact> tail(String answer) {
      return singleFact("tail", answer);
   }

   static List<Fact> voice(String answer) {
      return singleFact("voice", answer);
   }

   static List<Fact> season(String answer) {
      return singleFact("season", answer);
   }

   static List<Fact> cheek(String answer) {
      return singleFact("cheek", answer);
   }

   static List<Fact> flight_profile(String answer) {
      return singleFact("flight profile", answer);
   }

   static List<Fact> throat(String answer) {
      return singleFact("throat", answer);
   }

   static List<Fact> state(String answer) {
      return singleFact("state", answer);
   }

   static List<Fact> province(String answer) {
      return singleFact("province", answer);
   }

   static List<Fact> singleFact(String question, String answer) {
      return Collections.singletonList(new Fact(question, answer));
   }

   @SafeVarargs
   private static List<Fact> flatten(List<Fact>... facts) {
      return Arrays.stream(facts).flatMap(List::stream).collect(Collectors.toList());
   }
}
