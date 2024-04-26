import React from "react";
import { RestaurantCard } from "../components/RestaurantCard";
import Footer from "../components/Footer";
import "../classes.css";
import { Header } from "../components/Header";

export const SearchResult = () => {
  return (
    <body>
      <Header />
      <div class="container mt-2">
        <span class="ms-5">Results for # Restaurant Name</span>
        <div class="row justify-content-center">
          <RestaurantCard />
          <RestaurantCard />
          <RestaurantCard />
          <RestaurantCard />
          <RestaurantCard />
          <RestaurantCard />
          <RestaurantCard />
          <RestaurantCard />
        </div>
      </div>
      <Footer />
    </body>
  );
};
