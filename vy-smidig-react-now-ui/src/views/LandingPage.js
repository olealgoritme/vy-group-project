import React from "react";

import 'react-dates/lib/css/_datepicker.css';


// reactstrap components
import {
    Container,
} from "reactstrap";

// core components
import ExamplesNavbar from "components/Navbars/ExamplesNavbar.js";
import TransparentFooter from "components/Footers/TransparentFooter.js";
import DefaultDatePicker from "components/Datepicker/DefaultDatepicker.js";

function LandingPage() {
    const [firstFocus, setFirstFocus] = React.useState(false);
    const [lastFocus, setLastFocus] = React.useState(false);
    React.useEffect(() => {
        document.body.classList.add("login-page");
        document.body.classList.add("sidebar-collapse");
        document.documentElement.classList.remove("nav-open");
        window.scrollTo(0, 0);
        document.body.scrollTop = 0;
        return function cleanup() {
            document.body.classList.remove("login-page");
            document.body.classList.remove("sidebar-collapse");
        };
    })

    return (
        <>
            <ExamplesNavbar />
            <div className="page-header clear-filter" filter-color="blue">
                <div
                    className="page-header-image"
                    style={{
                        backgroundImage: "url(" + require("assets/img/login.jpg") + ")"
                    }}
                ></div>
                <Container>
                    <DefaultDatePicker />
                </Container>
                <TransparentFooter />
            </div>
        </>
    );
}

export default LandingPage;
