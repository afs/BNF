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

import java.util.function.Consumer;

public abstract class Modifier extends Expression {

    @Override
    public void printStructure(PrintFrame pFrame) {
        print(pFrame, true, expr->expr.printStructure(pFrame));
    }

    private void print(PrintFrame pFrame, boolean withNL, Consumer<Expression> action) {
        pFrame.out().print("(mod");
        pFrame.out().print(getMod());

        if ( withNL ) {
            pFrame.out().incIndent();
            pFrame.out().println();
        } else {
            pFrame.out().print(" ");
        }
        action.accept(getExpr());
        if ( withNL ) {
            pFrame.out().println();
            pFrame.out().decIndent();
//        } else {
//            pFrame.out().print(" ");
        }
        pFrame.out().println(")");
    }

    @Override
    final
    public boolean printAtomic(PrintFrame pFrame) {
        return true;
    }

    @Override
    final
    public void printAST(PrintFrame pFrame) {
        print(pFrame, false, expr->expr.printAST(pFrame));
    }

    @Override
    final
    public void printBNF(PrintFrame pFrame) {
        PrintFrame.printModifierEBNF(pFrame, getExpr(), getMod());
    }

    protected abstract Expression getExpr();
    protected abstract String getMod();


}
