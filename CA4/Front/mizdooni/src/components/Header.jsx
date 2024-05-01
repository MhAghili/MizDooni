import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../classes.css";
import mizdooniLogo from "../Statics/mizdooni_logo.png";
export const Header = (props) => {
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
          <button type="button" className="btn btn-danger rounded-2">
            {props.name}
          </button>
        </div>
      </div>
    </header>
  );
};
