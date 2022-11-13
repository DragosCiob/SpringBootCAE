
        create table users (
           user_id uuid not null,
            password varchar(64) not null,
            team varchar(255),
            first_name varchar(32) not null,
            second_name varchar(32) not null,
            username varchar(32) not null,
            primary key (user_id)
        );

        create table project_user_relation (
           project_id uuid not null,
            user_id uuid not null,
            primary key (project_id, user_id)
        );

        create table projects (
           project_id uuid not null,
            project_name varchar(64) not null,
            primary key (project_id)
        );


          create table requests (
             request_id uuid not null,
              request_index float8 not null,
              request_name varchar(32) not null,
              request_description varchar(255) not null,
              request_owner uuid,
              project_id uuid,
              primary key (request_id)
          );

        create table responses (
           response_id uuid not null,
            response_comment varchar(255) not null,
            response_type varchar(255),
            request_id uuid,
            response_owner uuid,
            primary key (response_id)
        );

            create table project_task (
               deadline date not null,
                start_date date not null,
                request_id uuid not null,
                primary key (request_id)
            );

