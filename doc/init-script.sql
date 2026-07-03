-- Create the user
CREATE USER "inscription-cours-avec-verification-user"
WITH PASSWORD 'poja-email';

-- Create the database
CREATE DATABASE "inscription-cours-avec-verification-db";

-- Give permissions to the user
GRANT ALL PRIVILEGES ON DATABASE "inscription-cours-avec-verification-db"
TO "inscription-cours-avec-verification-user";

-- Give schema permission
GRANT ALL ON SCHEMA public TO "inscription-cours-avec-verification-user";

-- Allow table creation
ALTER SCHEMA public OWNER TO "inscription-cours-avec-verification-user";

-- Allow future tables (optionnal bu recommended)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO "inscription-cours-avec-verification-user";
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO "inscription-cours-avec-verification-user";