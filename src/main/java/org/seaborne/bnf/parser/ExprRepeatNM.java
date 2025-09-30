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

import java.util.Objects;

public class ExprRepeatNM extends Expression {

    public static Expression create(Expression expr, String min, String max) {
        return new ExprRepeatNM(expr, min, max);
    }

    private final Expression expr;
    private final String min;
    private final String max;

    ExprRepeatNM(Expression expr, String min, String max) {
        Objects.requireNonNull(expr);
//        Objects.requireNonNull(min);
//        Objects.requireNonNull(max);
        this.expr = expr;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean printAtomic(PrintFrame pFrame) {
        return true;
    }

    @Override
    public void printStructure(PrintFrame pFrame) {
        String modifier = String.format("{%s,%s}", min, max);
        PrintFrame.printModifierFunction(pFrame, expr, modifier);
    }

    @Override
    public void printBNF(PrintFrame pFrame) {
        String modifier = String.format("{%s,%s}", min, max);
        PrintFrame.printModifierEBNF(pFrame, expr, modifier);
    }
}
