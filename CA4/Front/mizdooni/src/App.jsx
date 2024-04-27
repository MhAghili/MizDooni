import "./App.css";
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { ManagerManage } from "./Pages/ManagerManage";
import Home from "./Pages/Home";
import { SearchResult } from "./Pages/SearchResult";
import { Customer } from "./Pages/Customer";
import { Restaurant } from "./Pages/Restaurant";
import { MangerRestaurant } from "./Pages/MangerRestaurant";

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/Manager-Manage" element={<ManagerManage />} />
        <Route path="/Customer" element={<Customer />} />
        <Route path="/SearchResult" element={<SearchResult />} />
        <Route path="/Restaurant" element={<Restaurant />} />
        <Route path="/Manager-Restaurant" element={<MangerRestaurant />} />
      </Routes>
    </Router>
  );
}

export default App;
