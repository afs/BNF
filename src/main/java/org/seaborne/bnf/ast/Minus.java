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

import java.util.List;

public class Minus extends Expression {
    public final Expression expr1;
    public final Expression expr2;

    public static Expression create(Expression expr1, Expression expr2) {
        return new Minus(expr1, expr2);
    }

    private Minus(Expression expr1, Expression expr2) {
        this.expr1 = expr1 ;
        this.expr2 = expr2 ;
    }

    @Override
    public boolean printAtomic(PrintFrame pFrame) {
        return false;
    }

    @Override
    public void printAST(PrintFrame pFrame) {
        //PrintFrame.printListExpressions(pFrame, "Alt", alternatives);
        PrintFrame.printListAST(pFrame, "Minus", List.of(expr1, expr2));
    }

    @Override
    public void printStructure(PrintFrame pFrame) {
        PrintFrame.printListStructure(pFrame, "minus", List.of(expr1, expr2));

    }

    @Override
    public void printBNF(PrintFrame pFrame) {
        expr1.printBNF(pFrame);
        pFrame.out().print(" - ");
        expr2.printBNF(pFrame);
    }

}
