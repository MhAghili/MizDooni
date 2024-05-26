import Footer from "../components/Footer";
import "./Error.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "../classes.css";

const Error = () => {
  return (
    <>
      {" "}
      <div className="container error-container align-items-center">
        <div className="error-code">Error Message</div>
        <a href="/" className="back-link text-success">
          Back
        </a>
      </div>
      <Footer />
    </>
  );
};

export default Error;
