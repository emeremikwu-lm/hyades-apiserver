/*
 * This file is part of Dependency-Track.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright (c) OWASP Foundation. All Rights Reserved.
 */
package org.dependencytrack.tasks;

import alpine.event.LdapSyncEvent;
import alpine.event.framework.Event;
import alpine.event.framework.Subscriber;
import alpine.server.tasks.LdapSyncTask;
import org.dependencytrack.util.LockProvider;

import static org.dependencytrack.tasks.LockName.LDAP_SYNC_TASK_LOCK;

public class LdapSyncTaskWrapper implements Subscriber {

    private final LdapSyncTask ldapSyncTask;

    public LdapSyncTaskWrapper() {
        this(new LdapSyncTask());
    }

    LdapSyncTaskWrapper(LdapSyncTask ldapSyncTask) {
        this.ldapSyncTask = ldapSyncTask;
    }

    @Override
    public void inform(Event e) {
        if (e instanceof LdapSyncEvent) {
            LockProvider.executeWithLock(LDAP_SYNC_TASK_LOCK, (Runnable) () -> this.ldapSyncTask.inform(new LdapSyncEvent()));
        }
    }
}

