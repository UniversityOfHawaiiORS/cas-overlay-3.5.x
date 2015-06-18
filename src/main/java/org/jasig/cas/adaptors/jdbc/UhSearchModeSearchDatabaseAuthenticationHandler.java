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
package org.jasig.cas.adaptors.jdbc;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UhUsernamePasswordCredentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.InitializingBean;

import javax.validation.constraints.NotNull;

/**
 * Class that given a table, username field and password field will query a
 * database table with the provided encryption technique to see if the user
 * exists. This class defaults to a PasswordTranslator of
 * PlainTextPasswordTranslator.
 * 
 * @author Scott Battaglia
 * @author Dmitriy Kopylenko
 * @version $Revision$ $Date$
 * @since 3.0
 */

public class UhSearchModeSearchDatabaseAuthenticationHandler extends
    AbstractJdbcUsernamePasswordAuthenticationHandler implements InitializingBean {

    private static final String SQL_PREFIX = "Select count('x') from ";

    @NotNull
    private String fieldUser;

    @NotNull
    private String fieldPassword;

    @NotNull
    private String tableUsers;

    private String sql;

    protected final boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
        final String transformedUsername = getPrincipalNameTransformer().transform(credentials.getUsername());
        final String overrideUsername = ((UhUsernamePasswordCredentials)credentials).getOverrideUsername();
        final String password = credentials.getPassword();

        final String encyptedPassword = getPasswordEncoder().encode(password);

        String sqlQuery = this.sql;



        System.out.println("transformedUsername:" + transformedUsername);
        int count = getJdbcTemplate().queryForInt(sqlQuery,
           transformedUsername, encyptedPassword);

        // If userid:password didn't authenticate
        // Check if password is user:password for CAS backdoor feature
        if (count <= 0 && !overrideUsername.isEmpty()) {
            sqlQuery += " and backdoor_auth = 'Y' ";
            // Make sure back door name user exists to prevent failure after authentication
            String validateUserIdSql = "select count('x') from VALID_ENTITIES where PRNCPL_nm = ?";
            count = getJdbcTemplate().queryForInt(validateUserIdSql,transformedUsername);
            if (count > 0) {
                count = getJdbcTemplate().queryForInt(sqlQuery,
                        overrideUsername, encyptedPassword);
            }
        }

        return count > 0;
    }

    public void afterPropertiesSet() throws Exception {
        this.sql = SQL_PREFIX + this.tableUsers + " Where " + this.fieldUser
        + " = ? And " + this.fieldPassword + " = ?";
    }

    /**
     * @param fieldPassword The fieldPassword to set.
     */
    public final void setFieldPassword(final String fieldPassword) {
        this.fieldPassword = fieldPassword;
    }

    /**
     * @param fieldUser The fieldUser to set.
     */
    public final void setFieldUser(final String fieldUser) {
        this.fieldUser = fieldUser;
    }

    /**
     * @param tableUsers The tableUsers to set.
     */
    public final void setTableUsers(final String tableUsers) {
        this.tableUsers = tableUsers;
    }
}
