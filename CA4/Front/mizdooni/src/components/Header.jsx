import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../classes.css";
import mizdooniLogo from "../Statics/mizdooni_logo.png";
import { useNavigate } from "react-router-dom";
export const Header = (props) => {
  const navigate = useNavigate();
  const clickHandler = () => {
    console.log(props.name);
    if (props.name === "My Reserves") {
      navigate("/Customer");
    }
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
            />
            <span className="align-middle ms-2 text-danger">
              Reserve Table From Anywhere
            </span>
          </div>
        </div>
        <div className="col-4 text-end align-items-center">
          <span className="text-danger me-2 ">{props.descreption}</span>
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
