import "./App.css";
import React, { useEffect } from "react";
import { useData } from "./Data/DataContext";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { ManagerManage } from "./Pages/ManagerManage";
import Home from "./Pages/Home";
import { SearchResult } from "./Pages/SearchResult";
import { Customer } from "./Pages/Customer";
import { Restaurant } from "./Pages/Restaurant";
import { MangerRestaurant } from "./Pages/MangerRestaurant";
import Error from "./Pages/Error";
import { SignUpIn } from "./Pages/SignUpIn";

function App() {
  const { setRestaurantData } = useData();

  useEffect(() => {
    async function fetchData() {
      try {
        const restaurantDataResponse = await fetch(
          "http://localhost:8080/restaurants"
          ,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            }
          }
        );
        const restaurantData = await restaurantDataResponse.json();
        setRestaurantData(restaurantData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    }
    fetchData();
    console.log("data fetched!");
  }, []);
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<SignUpIn />} />
        <Route path="/Home" element={<Home />} />
        <Route path="/Manager-Manage" element={<ManagerManage />} />
        <Route path="/Customer" element={<Customer />} />
        <Route path="/SearchResult" element={<SearchResult />} />
        <Route path="/Restaurant" element={<Restaurant />} />
        <Route path="/Manager-Restaurant" element={<MangerRestaurant />} />
        <Route path="*" element={<Error />} />
      </Routes>
    </Router>
  );
}

export default App;
