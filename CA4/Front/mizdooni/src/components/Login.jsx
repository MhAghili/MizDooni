import React, { useState } from "react";
import { useData } from "../Data/DataContext";

export const Login = () => {
  const { userData } = useData();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        console.log("Login successful");
      } else {
        console.error("Login failed");
      }
    } catch (error) {
      console.error("Error occurred while logging in:", error);
    }
    setUsername("");
    setPassword("");
  };

  return (
    <>
      <div className="col-12 align-items-center ">
        <form className="cardRadius p-4" onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="exampleInputEmail1" className="form-label">
              Username
            </label>
            <input
              type="text"
              className="form-control bg-light"
              value={username}
              onChange={handleUsernameChange}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="exampleInputPassword1" className="form-label ">
              Password
            </label>
            <input
              type="password"
              className="form-control bg-light"
              value={password}
              onChange={handlePasswordChange}
            />
          </div>
          <div className="mb-3 text-center">
            <button type="submit" className="btn btn-danger col-9">
              Login
            </button>
          </div>
        </form>
      </div>
    </>
  );
};
