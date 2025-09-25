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

public class BNFParser {

    public void startGrammar() {}
    public void emitRule(Rule rule) { }
    public void finishGrammar() {}

    public void startRule() {}
    public Rule createRule(Identifier identifier, Expression expr) { return null; }
    public void finishRule() {}

    public void startExpression() {}
    public void finishExpression() {}

    public void startAlternatives() {}
    public void emitAlternativesElement(Expression expr) { }
    public Expression collectedAlternatives() { return null; }
    public void finishAlternatives() {}

    public void startSequence() {}
    public void emitSequenceElement(Expression expr) { }
    public Expression collectedSequence() { return null; }
    public void finishSequence() {}


    public Expression createExprZeroOrOne(Expression expr) { return null; }
    public Expression createExprZeroOrMore(Expression expr) { return null; }
    public Expression createExprOneOrMore(Expression expr) { return null; }

    public Identifier createNonTerminal(String string) { return null; }
    public Identifier createWord(String string) { return null; }
    public Expression createQuotedString(String string) { return null; }
}
