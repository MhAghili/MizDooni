import React, { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";

export const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const requestBody = JSON.stringify({ username, password });

      const response = await fetch("http://127.0.0.1:8080/users/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: requestBody,
      });
      if (response.ok) {
        localStorage.setItem("username", username);
        const UserRes = await fetch(`http://Localhost:8080/users/${username}`);
        const User = await UserRes.json();
        if (User.role === "manager") {
          navigate("/Manager-Restaurant");
        } else {
        }
        console.log("Login successful");
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.log(error);
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
