import React, { useState } from "react";
import "../App.css";
import "../classes.css";
import Footer from "../components/Footer";
import { Login } from "../components/Login";
import { SignUp } from "../components/SignUp";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const SignUpIn = () => {
  const [showSignup, setShowSignup] = useState(false);

  const toggleForm = () => {
    setShowSignup(!showSignup);
  };

  return (
    <>
      <div className="container">
        <div className="row justify-content-center ">
          <div className="col-6 pb-5 ">
            <div className="row mt-5 ">
              <button
                className={`col-6 rounded-start-3 py-3 border ${
                  showSignup ? "text-bg-danger" : ""
                }`}
                onClick={toggleForm}
              >
                SignUp
              </button>
              <button
                className={`col-6 rounded-end-3 py-3 border ${
                  showSignup ? "" : "text-bg-danger"
                }`}
                onClick={toggleForm}
              >
                Login
              </button>
            </div>
            <div className="row">{showSignup ? <SignUp /> : <Login />}</div>
          </div>
        </div>
      </div>
      <Footer />
      <ToastContainer />
    </>
  );
};
