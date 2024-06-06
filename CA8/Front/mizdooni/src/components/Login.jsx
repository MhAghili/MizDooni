import React, { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { useData } from "../Data/DataContext";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { setIsLogin } = useData();
  const googleClientId =
    "779637281034-0r9mqce17s07g21nnkr748p01ssgtnkq.apps.googleusercontent.com";
  const googleAuthUrl = `https://accounts.google.com/o/oauth2/auth?client_id=${googleClientId}&response_type=code&scope=email%20profile&redirect_uri=http://localhost:3000/callback`;

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

      const response = await fetch("http://127.0.0.1:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: requestBody,
        credentials: "include",
      });

      console.log(requestBody);
      if (response.ok) {
        const token = response?.headers?.get("Authorization").split(" ")[1];
        localStorage.setItem("token", token);
        localStorage.setItem("username", username);
        setIsLogin(true);
        const UserRes = await fetch(`http://127.0.0.1:8080/users/${username}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const User = await UserRes.json();
        if (User.role === "manager") {
          toast.success("Login successful");
          navigate("/Manager-Restaurant");
        } else {
          navigate("/Home");
        }
        console.log("Login successful");
      } else {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.log(error);
      toast.error(error);
    }
    setUsername("");
    setPassword("");
  };

  return (
    <>
      <div className="col-12 align-items-center justify-content-center ">
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
        <div className="row my-4 text-center">
          <span className="text-center col-12">
            ----------------------------------------------------------------------
          </span>
        </div>
        <div className="row justify-content-center text-center mt-5">
          <div className="col-11 text-center ">
            <a href={googleAuthUrl} className="text-decoration-none">
              <button type="submit" className="btn btn-danger col-9">
                Login via Google
              </button>
            </a>
          </div>
        </div>
      </div>
      <ToastContainer />
    </>
  );
};
