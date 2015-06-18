/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.authentication.principal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * UsernamePasswordCredentials respresents the username and password that a user
 * may provide in order to prove the authenticity of who they say they are.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.2 $ $Date: 2007/01/22 20:35:26 $
 * @since 3.0
 * <p>
 * This is a published and supported CAS Server 3 API.
 * </p>
 */
public class UhUsernamePasswordCredentials extends UsernamePasswordCredentials {
    /** The username. */
    private String overrideUsername;

    /**
     * @return Returns the overrideUsername.
     */
    public final String getOverrideUsername() {
        return this.overrideUsername;
    }

    /**
     * @param overrideUsername The userName to set.
     */
    public final void setOverrideUsername(final String overrideUsername) {
        this.overrideUsername = overrideUsername;
    }
}
