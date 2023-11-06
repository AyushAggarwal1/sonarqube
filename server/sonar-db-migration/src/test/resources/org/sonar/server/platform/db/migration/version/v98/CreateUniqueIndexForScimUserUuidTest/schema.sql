CREATE TABLE "SCIM_USERS"(
    "SCIM_UUID" CHARACTER VARYING(40) NOT NULL,
    "USER_UUID" CHARACTER VARYING(40) NOT NULL
);
ALTER TABLE "SCIM_USERS" ADD CONSTRAINT "PK_SCIM_USERS" PRIMARY KEY("SCIM_UUID");