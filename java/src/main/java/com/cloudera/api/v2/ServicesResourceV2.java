// Licensed to Cloudera, Inc. under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  Cloudera, Inc. licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.cloudera.api.v2;

import static com.cloudera.api.Parameters.SERVICE_NAME;

import javax.activation.DataSource;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.InputStreamDataSource;

import com.cloudera.api.model.ApiCommand;
import com.cloudera.api.model.ApiRoleNameList;
import com.cloudera.api.v1.ServicesResource;

@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface ServicesResourceV2 extends ServicesResource {

  /**
   * Download a zip-compressed archive of the client configuration,
   * of a specific service. This resource does not require any authentication.
   *
   * @param serviceName The service name.
   * @return The archive data.
   */
  @GET
  @PermitAll
  @Path("/{serviceName}/clientConfig")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public DataSource getClientConfig(
      @PathParam(SERVICE_NAME) String serviceName);

  /**
   * @return The roles resource handler.
   */
  @Override
  @Path("/{serviceName}/roles")
  public RolesResourceV2 getRolesResource(
      @PathParam(SERVICE_NAME) String serviceName);

  /**
   * Put the service into maintenance mode. This is a synchronous command. The
   * result is known immediately upon return.
   *
   * <p>
   * Available since API v2.
   * </p>
   *
   * @param serviceName The service name.
   * @return Synchronous command result.
   */
  @POST
  @Consumes
  @Path("/{serviceName}/commands/enterMaintenanceMode")
  public ApiCommand enterMaintenanceMode(
      @PathParam(SERVICE_NAME) String serviceName);

  /**
   * Take the service out of maintenance mode. This is a synchronous command.
   * The result is known immediately upon return.
   *
   * <p>
   * Available since API v2.
   * </p>
   *
   * @param serviceName The service name.
   * @return Synchronous command result.
   */
  @POST
  @Consumes
  @Path("/{serviceName}/commands/exitMaintenanceMode")
  public ApiCommand exitMaintenanceMode(
      @PathParam(SERVICE_NAME) String serviceName);

  /**
   * Recommission roles of a service.
   * <p>
   * The list should contain names of slave roles to recommission.
   * </p>
   *
   * <p>
   * Available since API v2.
   * </p>
   *
   * @param serviceName Name of the service on which to run the command.
   * @param roleNames List of role names to recommision.
   * @return Information about the submitted command.
   */
  @POST
  @Path("/{serviceName}/commands/recommission")
  public ApiCommand recommissionCommand(
      @PathParam(SERVICE_NAME) String serviceName,
      ApiRoleNameList roleNames);

  /**
   * Creates a tmp directory on the HDFS filesystem.
   * <p>
   * Available since API v2.
   * </p>
   *
   * @param serviceName Name of the HDFS service on which to run the command.
   * @return Information about the submitted command
   */
  @POST
  @Path("/{serviceName}/commands/hdfsCreateTmpDir")
  public ApiCommand hdfsCreateTmpDir(
      @PathParam(SERVICE_NAME) String serviceName);
  
  /**
   * Creates the Oozie Database Schema in the configured database.
   * This command does not create database. This command creates only tables
   * required by Oozie. To create database, please refer to oozieCreateEmbeddedDatabase()
   * 
   * <p>
   * Available since API v2.
   * </p>
   * 
   * @param serviceName Name of the Oozie service on which to run the command.
   * @return Information about the submitted command
   */
  @POST
  @Path("/{serviceName}/commands/createOozieDb")
  public ApiCommand createOozieDb(
      @PathParam(SERVICE_NAME) String serviceName);
}
