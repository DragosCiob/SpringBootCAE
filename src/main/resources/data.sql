--   populate user table
    INSERT INTO users(
	user_id, password, team, first_name, second_name, username)
	VALUES ('8410c252-8ec3-44e4-9c93-35775f32c687', '$2y$10$MsMuWIM8urx7WEO2FBqude3jev2KTFOMf6vy9Og4qXHrBRI7YlVTq', 'CAELEAD', 'Magdalena', 'Ricard', 'magdalenaR');
	INSERT INTO users(
    user_id, password, team, first_name, second_name, username)
    VALUES ('d0742d47-0609-4774-ace3-9da696f698df', '$2y$10$IiJvr2kYeOiFvfARz/Woc.uZ/5lC5bT5WvdrJqTKQBEKigrS/tLYm', 'CAELEAD', 'Thomas', 'Gall', 'thomasG');


    	INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('e0954b61-88a3-4a16-8d6e-a36becc45f18', '$2y$10$nSUbPMY6w166ZUAGvNupZ.rbZf10Qy1hrrARc6pgeqWQyqM837JcC', 'CFD', 'Dragos', 'Ciobanu', 'ciobanuD');
        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('91920fe5-6443-4172-8e31-c325b1c93fab', '$2y$10$C3T2zdfEHdazL6e96qZ8VO1H6/oKsqyuU02rjhlkIQ6qfV0uuhPJ2', 'CFD', 'Eder', 'Mann', 'ederM');

        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('38909909-1713-40dc-977b-a46f291ed303', '$2y$10$RdRf3m/ZL6w4a3m/bt5gC.wN25V6OZ6bjdQiwOPjmbJDyKhRNsmzi', 'VALIDATION', 'Hans', 'Jirowez', 'hansJ');
        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('e1acc4ef-5e4d-4f50-b064-79ca8fdabae7', '$2y$10$YFZKaqjtTzDxh/z3v9WOKe8zgHRyS7CJDphbOq2mSFmn7ngP/gBc.', 'VALIDATION', 'Ana', 'Sandu', 'anaS');

        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('0ed7f953-6bc3-4d46-b90a-02a6519ede4a', '$2y$10$RdRf3m/ZL6w4a3m/bt5gC.wN25V6OZ6bjdQiwOPjmbJDyKhRNsmzi', 'MECHANICAL', 'Bjorn', 'Damsgard', 'bjornD');
        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('358055ce-be94-4aad-85b8-73722b0c3a4d', '$2y$10$asr1OMbqZ9UL2JwpC8Sk/eOtqIgxRc6Si43zyjdGGQQzXShP.cPh2', 'MECHANICAL', 'Sara', 'Arrancci', 'saraA');

        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('70edb7cf-4138-4eb9-956c-8df0221e7de0', '$2y$10$kimCXuvMePcSBugZbAep7OOfTuf7trfGlLWY8F8boalYrC38Z.TK.', 'NVH', 'Marcello', 'Solara', 'marcelloS');
        INSERT INTO users(
        user_id, password, team, first_name, second_name, username)
        VALUES ('4312049a-e5d4-4c6d-958e-c5f8b74397d4', '$2y$10$hiYcmhmBNAG72oi7VXwnVejaN/X9ggGd5RXNHy99NA./PIVeVu6o.', 'NVH', 'Natalie', 'Ruth', 'natalieR');

--   populate projects table

    INSERT INTO projects(
    	project_id, project_name)
    	VALUES ('40f3100a-e01b-49e2-9ebe-1fc53f855714', 'Daimler');

--  populate team in project

    INSERT INTO project_user_relation(
    	project_id, user_id)
    	VALUES ('40f3100a-e01b-49e2-9ebe-1fc53f855714', '8410c252-8ec3-44e4-9c93-35775f32c687');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('40f3100a-e01b-49e2-9ebe-1fc53f855714', 'e0954b61-88a3-4a16-8d6e-a36becc45f18');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('40f3100a-e01b-49e2-9ebe-1fc53f855714', '38909909-1713-40dc-977b-a46f291ed303');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('40f3100a-e01b-49e2-9ebe-1fc53f855714', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('40f3100a-e01b-49e2-9ebe-1fc53f855714', '70edb7cf-4138-4eb9-956c-8df0221e7de0');

--  populate requests table

    INSERT INTO requests(
    	request_id, request_index, request_name, request_description, request_owner, project_id)
    	VALUES ('3b8cd965-9822-4049-89fe-bebb06f6d90a', '1.0', 'Daimler1', 'Daimler1', '8410c252-8ec3-44e4-9c93-35775f32c687', '40f3100a-e01b-49e2-9ebe-1fc53f855714');

--  populate response table
INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('b686aa69-4b05-47b8-80a7-945e83074176', 'Approved', 'APPROVED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '8410c252-8ec3-44e4-9c93-35775f32c687');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('46623cf8-9e2f-4656-b295-df8d22170bab', 'Approved', 'APPROVED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '38909909-1713-40dc-977b-a46f291ed303');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('6d70f2c0-88d6-4771-993e-43f12ca89463', 'Bad idea!', 'REJECTED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('0d269c34-e1cb-43c4-873d-bd699103b4d2', 'Bad idea!', 'REJECTED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '70edb7cf-4138-4eb9-956c-8df0221e7de0');