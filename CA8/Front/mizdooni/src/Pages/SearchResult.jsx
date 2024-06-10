import React, { useEffect, useState } from "react";
import { RestaurantCard } from "../components/RestaurantCard";
import Footer from "../components/Footer";
import "../classes.css";
import { Header } from "../components/Header";
import { useLocation } from "react-router-dom";

export const SearchResult = () => {
  const { searchInput, searchBy } = useLocation().state;
  const [searchedRestaurants, setSearchedRestaurants] = useState([]);
  useEffect(() => {
    async function fetchData() {
      try {
        let searchedRes = [];
        if (searchBy === "name") {
          searchedRes = await fetch(
            `http://localhost:8093/restaurants/RestaurantName=${searchInput}`,
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
              },
            }
          ).then((res) => res.json());
          console.log(searchedRes);
        } else {
          searchedRes = await fetch(
            `http://127.0.0.1:8093/restaurants/type=${searchInput}`,
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
              },
            }
          ).then((res) => res.json());
          console.log(searchInput, searchedRes);
        }

        setSearchedRestaurants(searchedRes);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }
    fetchData();
    console.log("data fetched!");
  }, []);
  return (
    <body>
      <Header name={"My Reserves"} />
      <div className="container mt-2">
        <span className="ms-5">
          Results for <span className="text-primary">{searchInput}</span>{" "}
          Restaurant <span className="text-primary">{searchBy}</span>
        </span>
        <div className="row justify-content-center">
          {searchedRestaurants.length !== 0 ? (
            searchedRestaurants.map((restaurant) => (
              <RestaurantCard
                key={restaurant.id}
                name={restaurant.name}
                address={restaurant.address}
                image={restaurant.image}
                type={restaurant.type}
                endTime={restaurant.endTime}
              />
            ))
          ) : (
            <span className="text-center text-danger fs-3 pt-3">
              No results found
            </span>
          )}
          {console.log(searchedRestaurants)}
        </div>
      </div>
      <Footer />
    </body>
  );
};
