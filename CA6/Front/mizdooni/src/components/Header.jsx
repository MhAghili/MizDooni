import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../classes.css";
import mizdooniLogo from "../Statics/mizdooni_logo.png";
import { useNavigate } from "react-router-dom";
import { useData } from "../Data/DataContext";
export const Header = (props) => {
  const navigate = useNavigate();
  const { isLogin, setIsLogin } = useData();
  const clickHandler = () => {
    if (props.name === "My Reserves") {
      navigate("/Customer");
    } else if (props.name === "My Restaurants") {
      navigate("/Manager-Restaurant");
    }
  };

  const logoutHandler = () => {
    localStorage.removeItem("username");
    setIsLogin(false);
    navigate("/");
  };

  return (
    <header className="navbar navbar-expand-lg navbar-light bg-light px-5 py-3 position-sticky">
      <div className="container-fluid">
        <div className="col-4">
          <div className="d-flex align-items-center">
            <img
              src={mizdooniLogo}
              className="mizdooniHeader"
              alt="Mizdooni Logo"
              onClick={() => navigate("/Home")}
              style={{ cursor: "pointer" }}
            />
            <span className="align-middle ms-2 text-danger">
              Reserve Table From Anywhere
            </span>
          </div>
        </div>
        <div className="col-4 text-end align-items-center">
          <span className="text-danger me-2 ">{props.descreption}</span>
          {isLogin && (
            <button
              onClick={logoutHandler}
              type="button"
              className="btn btn-danger rounded-2 me-2"
            >
              Logout
            </button>
          )}

          <button
            onClick={clickHandler}
            type="button"
            className="btn btn-danger rounded-2"
          >
            {props.name}
          </button>
        </div>
      </div>
    </header>
  );
};
