/*
 * SonarQube
 * Copyright (C) 2009-2023 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.db.rule;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sonar.api.code.CodeCharacteristic;
import org.sonar.api.rules.RuleType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sonar.db.rule.RuleTypeToCodeCharacteristicConverter.convertToCodeCharacteristic;

@RunWith(DataProviderRunner.class)
public class RuleTypeToCodeCharacteristicConverterTest {

  @Test
  @UseDataProvider("ruleTypeToCodeCharacteristicData")
  public void convertToCodeCharacteristic_when_receivedNonNullRuleType_should_convertToCorrespondingDefaultCharacteristic(RuleType type,
    CodeCharacteristic expectedCharacteristic) {
    assertThat(convertToCodeCharacteristic(type)).isEqualTo(expectedCharacteristic);
  }

  @DataProvider
  public static Object[][] ruleTypeToCodeCharacteristicData() {
    return new Object[][] {
      {RuleType.CODE_SMELL, CodeCharacteristic.CLEAR},
      {RuleType.BUG, CodeCharacteristic.ROBUST},
      {RuleType.VULNERABILITY, CodeCharacteristic.SECURE},
      {RuleType.SECURITY_HOTSPOT, CodeCharacteristic.SECURE}
    };
  }

}