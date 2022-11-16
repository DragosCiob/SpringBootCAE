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

--   populate projects table:Daimler

    INSERT INTO projects(
    	project_id, project_name)
    	VALUES ('3eedec7d-76f9-42aa-8f40-46481416c51d', 'Daimler');

--  populate team in project

    INSERT INTO project_user_relation(
    	project_id, user_id)
    	VALUES ('3eedec7d-76f9-42aa-8f40-46481416c51d', 'd0742d47-0609-4774-ace3-9da696f698df');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('3eedec7d-76f9-42aa-8f40-46481416c51d', 'e0954b61-88a3-4a16-8d6e-a36becc45f18');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('3eedec7d-76f9-42aa-8f40-46481416c51d', '38909909-1713-40dc-977b-a46f291ed303');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('3eedec7d-76f9-42aa-8f40-46481416c51d', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('3eedec7d-76f9-42aa-8f40-46481416c51d', '70edb7cf-4138-4eb9-956c-8df0221e7de0');

--  populate requests table

    INSERT INTO requests(
    	request_id, request_index, request_name, request_description, request_owner, project_id)
    	VALUES ('3b8cd965-9822-4049-89fe-bebb06f6d90a', '1.0', 'Daimler-Junction Box', 'Boundary conditions: .....................    |3DData on this path:..........',
    	 'd0742d47-0609-4774-ace3-9da696f698df', '3eedec7d-76f9-42aa-8f40-46481416c51d');

--  populate response table
INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('b686aa69-4b05-47b8-80a7-945e83074176', 'Approved', 'APPROVED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', 'd0742d47-0609-4774-ace3-9da696f698df');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('46623cf8-9e2f-4656-b295-df8d22170bab', 'Approved', 'APPROVED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '38909909-1713-40dc-977b-a46f291ed303');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('6d70f2c0-88d6-4771-993e-43f12ca89463', 'Bad idea!', 'REJECTED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('0d269c34-e1cb-43c4-873d-bd699103b4d2', 'Bad idea!', 'REJECTED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', '70edb7cf-4138-4eb9-956c-8df0221e7de0');

--INSERT INTO responses(
--	response_id, response_comment, response_type, request_id, response_owner)
--	VALUES ('5871e60f-b470-47a5-9303-350f99dff563', 'Approved!', 'APPROVED', '3b8cd965-9822-4049-89fe-bebb06f6d90a', 'e0954b61-88a3-4a16-8d6e-a36becc45f18');





--   populate projects table:BMW

    INSERT INTO projects(
    	project_id, project_name)
    	VALUES ('0e9379fa-1332-4eed-9145-6066953c503c', 'BMW');

--  populate team in project

    INSERT INTO project_user_relation(
    	project_id, user_id)
    	VALUES ('0e9379fa-1332-4eed-9145-6066953c503c', 'd0742d47-0609-4774-ace3-9da696f698df');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('0e9379fa-1332-4eed-9145-6066953c503c', 'e0954b61-88a3-4a16-8d6e-a36becc45f18');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('0e9379fa-1332-4eed-9145-6066953c503c', '38909909-1713-40dc-977b-a46f291ed303');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('0e9379fa-1332-4eed-9145-6066953c503c', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('0e9379fa-1332-4eed-9145-6066953c503c', '70edb7cf-4138-4eb9-956c-8df0221e7de0');

--  populate requests table

    INSERT INTO requests(
    	request_id, request_index, request_name, request_description, request_owner, project_id)
    	VALUES ('75ce03b8-ff4a-4314-8ff4-3378301975e3', '1.0', 'BMW-Charging path', 'Boundary conditions: .....................    |3DData on this path:..........',
    	 'd0742d47-0609-4774-ace3-9da696f698df', '0e9379fa-1332-4eed-9145-6066953c503c');

--  populate response table
INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('c5dfa88a-1dd3-47e5-8339-f771f49747b8', 'Approved', 'APPROVED', '75ce03b8-ff4a-4314-8ff4-3378301975e3', '8410c252-8ec3-44e4-9c93-35775f32c687');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('6927b29d-d4a6-479d-875b-82547e0dfe8f', 'Approved', 'APPROVED', '75ce03b8-ff4a-4314-8ff4-3378301975e3', '38909909-1713-40dc-977b-a46f291ed303');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('1c70c43f-cf96-4990-b83f-ab660294701b', 'Bad idea!', 'REJECTED', '75ce03b8-ff4a-4314-8ff4-3378301975e3', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('0cec449b-dc28-4dba-b235-686bfc2b0a82', 'Bad idea!', 'REJECTED', '75ce03b8-ff4a-4314-8ff4-3378301975e3', '70edb7cf-4138-4eb9-956c-8df0221e7de0');


--   populate projects table:vw

    INSERT INTO projects(
    	project_id, project_name)
    	VALUES ('5241c467-7206-43d0-b5ba-136597e0a92a', 'VW');

--  populate team in project

    INSERT INTO project_user_relation(
    	project_id, user_id)
    	VALUES ('5241c467-7206-43d0-b5ba-136597e0a92a', '8410c252-8ec3-44e4-9c93-35775f32c687');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('5241c467-7206-43d0-b5ba-136597e0a92a', 'e0954b61-88a3-4a16-8d6e-a36becc45f18');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('5241c467-7206-43d0-b5ba-136597e0a92a', '38909909-1713-40dc-977b-a46f291ed303');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('5241c467-7206-43d0-b5ba-136597e0a92a', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

    INSERT INTO project_user_relation(
        	project_id, user_id)
        	VALUES ('5241c467-7206-43d0-b5ba-136597e0a92a', '70edb7cf-4138-4eb9-956c-8df0221e7de0');

--  populate requests table second

    INSERT INTO requests(
    	request_id, request_index, request_name, request_description, request_owner, project_id)
    	VALUES ('b8c49b5c-349a-44fa-a278-1585694b2f0e', '2.0', 'VW-Fuse box', 'Boundary conditions: .....................    |3DData on this path:..........',
    	 '8410c252-8ec3-44e4-9c93-35775f32c687', '5241c467-7206-43d0-b5ba-136597e0a92a');

--  populate response table
INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('da06fbf5-50b6-40b6-a36f-da0875e5583b', 'Approved', 'APPROVED', 'b8c49b5c-349a-44fa-a278-1585694b2f0e', '8410c252-8ec3-44e4-9c93-35775f32c687');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('7e1fbd9d-b41f-4e3a-be5f-c076df1a454d', 'Approved', 'APPROVED', 'b8c49b5c-349a-44fa-a278-1585694b2f0e', '38909909-1713-40dc-977b-a46f291ed303');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('938f091f-aa7c-4311-87ae-7bf1b8c6aa06', 'Bad idea!', 'REJECTED', 'b8c49b5c-349a-44fa-a278-1585694b2f0e', '0ed7f953-6bc3-4d46-b90a-02a6519ede4a');

INSERT INTO responses(
	response_id, response_comment, response_type, request_id, response_owner)
	VALUES ('26d7b945-b408-4bfe-87c8-1780671a9da5', 'Bad idea!', 'REJECTED', 'b8c49b5c-349a-44fa-a278-1585694b2f0e', '70edb7cf-4138-4eb9-956c-8df0221e7de0');