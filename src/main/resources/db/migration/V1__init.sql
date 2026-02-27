CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE workout_plans (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   name VARCHAR(255) NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_workout_plan_user
       FOREIGN KEY (user_id) REFERENCES users(id)
           ON DELETE CASCADE
);

CREATE TABLE exercises (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(255) NOT NULL,
   muscle_group VARCHAR(100),
   equipment VARCHAR(100)
);

CREATE TABLE workout_exercises (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   workout_plan_id BIGINT NOT NULL,
   exercise_id BIGINT NOT NULL,
   order_index INT NOT NULL,
   CONSTRAINT fk_workout_exercise_plan
       FOREIGN KEY (workout_plan_id) REFERENCES workout_plans(id)
           ON DELETE CASCADE,
   CONSTRAINT fk_workout_exercise_exercise
       FOREIGN KEY (exercise_id) REFERENCES exercises(id)
           ON DELETE CASCADE
);

CREATE TABLE workout_sets (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  workout_exercise_id BIGINT NOT NULL,
  set_number INT NOT NULL,
  repetitions INT NOT NULL,
  weight DECIMAL(6,2),
  rest_seconds INT,
  CONSTRAINT fk_workout_set_workout_exercise
      FOREIGN KEY (workout_exercise_id) REFERENCES workout_exercises(id)
          ON DELETE CASCADE
);