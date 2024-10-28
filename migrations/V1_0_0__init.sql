create schema if not exists workspace;

-- create user
create table if not exists workspace.user (
  id text PRIMARY KEY UNIQUE NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  username text not null UNIQUE,
  display_name text not null,
  email text not null
);

-- create ticket
create table if not exists workspace.ticket (
  id text PRIMARY KEY UNIQUE,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  title text not null,
  sprint text,
  type text,
  status text,
--  FOREIGN KEY(reporter_id) REFERENCES workspace.user(id),
--  FOREIGN KEY(assignee_id) REFERENCES workspace.user(id),
  assignee_id text,
  reporter_id text not null,
  tags character varying(20)[] not null,
  parent_id text
);

create table if not exists workspace.ticket_details (
  ticket text PRIMARY KEY UNIQUE,
  FOREIGN KEY(ticket) REFERENCES workspace.ticket(id),
  description text,
  subtasks text[] not null,
  chat_id text not null
);

-- create index for better filters performance
CREATE INDEX ticket_tags_array_x ON workspace.ticket USING GIN (tags);

-- create chat
create table if not exists workspace.chat_messages (
  id text PRIMARY KEY UNIQUE,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  chat_id text not null,
  sender_id text not null,
  message text,
  FOREIGN KEY(sender_id) REFERENCES workspace.user(id)
);

create table public.users(
	username varchar(50) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);


create table public.authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references public.users(username)
);
create unique index ix_auth_username on authorities (username,authority);