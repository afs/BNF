/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.seaborne.bnf.parser;

import static java.lang.String.format;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.seaborne.bnf.Grammar;
import org.seaborne.bnf.parser.javacc.ParseException;

public class BNFParser {

    public Grammar getGrammar() { return Grammar.create(accRules); }


    private List<Rule> accRules = new ArrayList<>();
    protected void startGrammar() {}
    protected void emitRule(Rule rule) { accRules.add(rule); }
    protected void finishGrammar() {}

    protected void startRule() {}

    protected Rule createRule(String parsedLabel, Identifier identifier, Expression expr) {
        //String label = removeFirstLast(parsedLabel);
        return Rule.create(parsedLabel, identifier, expr);
    }

    protected void finishRule() {}

    protected void startExpression() {}
    protected void finishExpression() {}

    // This could happen on the parse stack.
    protected void startAlternatives() {
        alternativeAccumulators.addFirst(new ArrayList<>());
    }

    private Deque<List<Expression>> alternativeAccumulators = new ArrayDeque<>();
    protected void emitAlternativesElement(Expression expr) {
        alternativeAccumulators.getFirst().add(expr);
    }

    protected Expression collectedAlternatives() {
        return Alternatives.create(alternativeAccumulators.getFirst());
    }

    protected void finishAlternatives() {
        alternativeAccumulators.removeFirst();
    }

    private Deque<List<Expression>> sequenceAccumulators = new ArrayDeque<>();
    protected void startSequence() { sequenceAccumulators.addFirst(new ArrayList<>()); }
    protected void emitSequenceElement(Expression expr) { sequenceAccumulators.getFirst().add(expr); }
    protected Expression collectedSequence() { return Sequence.create(sequenceAccumulators.getFirst()); }
    protected void finishSequence() { sequenceAccumulators.removeFirst(); }


    protected Expression createExprZeroOrOne(Expression expr) {
        return ExprZeroOrOne.create(expr);
    }

    protected Expression createExprZeroOrMore(Expression expr) {
        return ExprZeroOrMore.create(expr);
    }

    protected Expression createExprOneOrMore(Expression expr) {
        return ExprOneOrMore.create(expr);
    }

    protected Identifier createNonTerminal(String string) {
        return new Identifier(removeFirstLast(string));
    }

    protected Identifier createWord(String string) {
        return new Identifier(string);
    }

    protected Expression createPrimary(Expression expr) {
        return new Primary(expr);
    }

    // Esacpes.

    protected Expression createQuotedString(String string) {
        return new QuotedString(removeFirstLast(string));
    }

    protected Expression createCharacter(String string) {
        return new HexCharacter(string);
    }

    protected Expression createCharacterRange(String string) {
        // Image includes the "[...]".
        String x = string.substring(1, string.length()-1);
        return new CharRange(x);
    }

//    protected Expression createCharacterRange(String string1, String string2, boolean isNegative) {
//        return new CharRange(string1, string2, isNegative);
//    }

    // Replace by lexical state.
    protected String wordToRangeChar(String string, int line, int column) throws ParseException {
        if ( string.length() != 1 )
            throw new ParseException(format("[%d, %d] Not a range character '%s'", line, column, string));
        return string;
    }

    protected Expression createExprRepeat(Expression expr, String str1, String str2) {
        if ( str2 == null )
            return new ExprRepeatN(expr, str1);
        else
            return new ExprRepeatNM(expr, str1, str2);
    }


//    protected Expression createPositiveCharRange(String string1, String string2) {
//        return new CharRange(string1, string2);
//    }
//
//    protected Expression createPositiveHexCharRange(String string1, String string2) {
//        return new CharRange(string1, string2);
//    }

    /**
     * Remove the first and last characters of the argument.
     *
     * The argument is assumed to be validated, e.g. does boundary
     * characters and is at least 2 characters long.
     */
    private static String removeFirstLast(String string) {
        if ( string == null || string.length() < 2 )
            return null;
        return string.substring(1, string.length()-1);
    }
}
