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

package org.seaborne.bnf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seaborne.bnf.ast.Identifier;
import org.seaborne.bnf.ast.Rule;

/**
 * A grammar is a collection of {@link Rule} elements.
 */
public class Grammar {
    private final List<Rule> rules;
    private final Map<String, Rule> idToRule;

    public static Grammar create(List<Rule> rules) {
        return new Grammar(List.copyOf(rules));
    }

    private Grammar(List<Rule> rules) {
        this.rules = rules;
        Map<String, Rule> findRule = new HashMap<>();
        rules.forEach(rule-> findRule.put(rule.getIdentifier().getString(), rule));
        idToRule = Map.copyOf(findRule);
    }

    public List<Rule> rules() { return rules; }

    public Rule getRule(Identifier identifier) { return idToRule.get(identifier.getString()); }

    public Rule getRule(String identifier) { return idToRule.get(identifier); }
}
