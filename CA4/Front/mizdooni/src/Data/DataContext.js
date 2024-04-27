import React, { createContext, useState, useContext } from "react";

const DataContext = createContext();

export const DataProvider = ({ children }) => {
  const [userData, setUserData] = useState([]);
  const [tableData, setTableData] = useState([]);
  const [restaurantData, setRestaurantData] = useState([]);

  return (
    <DataContext.Provider
      value={{ userData, setUserData, tableData, setTableData, restaurantData, setRestaurantData }}
    >
      {children}
    </DataContext.Provider>
  );
};

export const useData = () => useContext(DataContext);
