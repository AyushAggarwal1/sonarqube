/*
 * SonarQube
 * Copyright (C) 2009-2022 SonarSource SA
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
package org.sonar.server.pushapi.issues;

import java.util.Set;
import java.util.function.Predicate;
import javax.servlet.AsyncContext;
import org.junit.Test;
import org.sonar.core.util.issue.Issue;
import org.sonar.core.util.issue.IssueChangedEvent;
import org.sonar.server.pushapi.sonarlint.SonarLintClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class IssueChangeBroadcastUtilsTest {

  private final String PROJECT_KEY = "projectKey";
  private final String USER_UUID = "userUUID";
  private final AsyncContext asyncContext = mock(AsyncContext.class);

  @Test
  public void getsFilterForEvent() {
    Issue[] issues = new Issue[]{ new Issue("issue-1", "branch-1")};
    IssueChangedEvent issueChangedEvent = new IssueChangedEvent(PROJECT_KEY, issues, true, "BLOCKER", "BUG");
    Predicate<SonarLintClient> predicate = IssueChangeBroadcastUtils.getFilterForEvent(issueChangedEvent);
    assertThat(predicate.test(new SonarLintClient(asyncContext, Set.of(PROJECT_KEY), Set.of(), USER_UUID))).isTrue();
    assertThat(predicate.test(new SonarLintClient(asyncContext, Set.of(), Set.of(), USER_UUID))).isFalse();
    assertThat(predicate.test(new SonarLintClient(asyncContext, Set.of("another-project"), Set.of(), USER_UUID))).isFalse();
  }

  @Test
  public void getsMessageForEvent() {
    Issue[] issues = new Issue[]{ new Issue("issue-1", "branch-1")};
    IssueChangedEvent issueChangedEvent = new IssueChangedEvent(PROJECT_KEY, issues, true, "BLOCKER", "BUG");
    String message = IssueChangeBroadcastUtils.getMessage(issueChangedEvent);

    assertThat(message).isEqualTo("event: IssueChangedEvent\n" +
      "data: {\"projectKey\":\""+ PROJECT_KEY+"\"," +
      "\"userType\":\"BUG\"," +
      "\"issues\":[{\"issueKey\":\"issue-1\",\"branchName\":\"branch-1\"}]," +
      "\"userSeverity\":\"BLOCKER\"," +
      "\"resolved\":true}");
  }
}