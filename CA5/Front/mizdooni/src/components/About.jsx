import React from "react";
import Banner from "../Statics/Banner.svg";
import "../classes.css";


export const About = () => {
  return (
    <div className="container-fluid">
      <div className="row justify-content-center">
        <div className="col-8 d-flex">
          <div className="col-7">
            <img src={Banner} className="tableSize" alt="" />
          </div>
          <div className="col-6 pt-5">
            <span className="text-danger fs-4">About Mizdooni</span>
            <br />
            <p className="darkGray">
              Are you tired of waiting in long lines at restaurants or
              struggling to find a table at your favorite eatery? Look no
              further than Mizdooni – the ultimate solution for all your dining
              reservation needs. Mizdooni is a user-friendly website where you
              can reserve a table at any restaurant, from anywhere, at a
              specific time. Whether you're craving sushi, Italian, or a quick
              bite to eat, Mizdooni has you covered. With a simple search
              feature, you can easily find a restaurant based on cuisine or
              location. The best part? There are no fees for making a
              reservation through Mizdooni. Say goodbye to the hassle of calling
              multiple restaurants or showing up only to find there's a long
              wait.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
