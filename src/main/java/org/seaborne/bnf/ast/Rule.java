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

package org.seaborne.bnf.ast;

public class Rule {

    public static Rule create(String label, Identifier identifier, Expression expr) {
        // Check label
        return new Rule(label, identifier, expr);
    }

    private final String label;
    private final Identifier identifier;
    private final Expression expression;
    private final String defaultLabel = null;

    public Rule(String label, Identifier identifier, Expression expr) {
        this.label = label;
        this.identifier = identifier;
        this.expression = expr;
    }

//    // Without recording shape.
//    public Rule pure() {
//        // No "Primary"
//    }

    private static int RULE_BODY_OFFSET = 4;

    /**
     * Non-standard, non-parsable format that makes the structure (sequences and alternations) explicit.
     */
    public void printStructure(PrintFrame pFrame) {
        printLabel(pFrame, label);
        pFrame.out().print(identifier.getString());
        pFrame.out().print(" ::=");
        pFrame.out().println();
        pFrame.out().incIndent(RULE_BODY_OFFSET);
        expression.printStructure(pFrame);
        pFrame.out().decIndent(RULE_BODY_OFFSET);
    }

    /**
     * Non-standard, AST
     */
    public void printAST(PrintFrame pFrame) {
        printLabel(pFrame, label);
        pFrame.out().print(identifier.getString());
        pFrame.out().print(" ::= ");
        expression.printAST(pFrame);
    }

    /**
     * EBNF
     */
    public void printEBNF(PrintFrame pFrame) {
        printLabel(pFrame, label);
        pFrame.out().print(identifier.getString());
        pFrame.out().print(" ::= ");
        // No need for brackets here
        expression.printBNF(pFrame);
    }

    private void printLabel(PrintFrame pFrame, String label) {
        if ( label == null && defaultLabel == null )
            return;

        String x;
        if (label != null )
            x = String.format("~%s~ ", label);
            //x = String.format("@%s", label);
        else
            x = defaultLabel+" ";
        pFrame.out().printf(pFrame.labelFmt(), x);
    }

    public String getLabel() {
        return label;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}
