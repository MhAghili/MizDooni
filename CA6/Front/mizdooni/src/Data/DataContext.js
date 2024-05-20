import React, { createContext, useState, useContext } from "react";

const DataContext = createContext();

export const DataProvider = ({ children }) => {
  const [restaurantData, setRestaurantData] = useState([]);
  const [isLogin, setIsLogin] = useState(false);

  return (
    <DataContext.Provider
      value={{ restaurantData, setRestaurantData, isLogin, setIsLogin }}
    >
      {children}
    </DataContext.Provider>
  );
};

export const useData = () => useContext(DataContext);
