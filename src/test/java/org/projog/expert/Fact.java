package org.projog.expert;

/** Used by tests to group an attribute with the value the test will select for it. */
class Fact {
   private final String attribute;
   private final String answer;

   Fact(String attribute, String answer) {
      this.attribute = attribute;
      this.answer = answer;
   }

   String getAttribute() {
      return attribute;
   }

   String getAnswer() {
      return answer;
   }
}
