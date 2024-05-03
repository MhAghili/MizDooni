import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../classes.css";
import SharpSign from "../Statics/SharpSign.png";
import Sofa from "../Statics/Sofa.png";
export const ReserveCard = (props) => {
  return (
    <div className="col-md-auto darkRed border cardRadius m-3">
      <div className="container-fluid p-2">
        <div className="row ms-1 mb-1 align-items-center">
          <div className="col-auto">
            <img src={SharpSign} alt="" />
          </div>
          <div className="col-auto text-light">{props.table}</div>
        </div>
        <div className="row ms-1 mb-1 align-items-center">
          <div className="col-auto">
            <img src={Sofa} alt="" />
          </div>
          <div className="col-auto text-light">{props.seat}</div>
        </div>
      </div>
    </div>
  );
};
