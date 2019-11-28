import React from "react";
import {Link} from "react-router-dom";
// reactstrap components
import {
    Collapse,
    DropdownToggle,
    DropdownMenu,
    DropdownItem,
    UncontrolledDropdown,
    NavbarBrand,
    Navbar,
    NavItem,
    NavLink,
    Nav,
    Container,
    UncontrolledTooltip
} from "reactstrap";

function ExamplesNavbar() {
    const [navbarColor, setNavbarColor] = React.useState("navbar-transparent");
    const [collapseOpen, setCollapseOpen] = React.useState(false);
    React.useEffect(() => {
        const updateNavbarColor = () => {
            if (
                document.documentElement.scrollTop > 399 ||
                document.body.scrollTop > 399
            ) {
                setNavbarColor("");
            } else if (
                document.documentElement.scrollTop < 400 ||
                document.body.scrollTop < 400
            ) {
                setNavbarColor("navbar-transparent");
            }
        };
        window.addEventListener("scroll", updateNavbarColor);
        return function cleanup() {
            window.removeEventListener("scroll", updateNavbarColor);
        };
    });
    return (
        <>
            {collapseOpen ? (
                <div
                    id="bodyClick"
                    onClick={() => {
                        document.documentElement.classList.toggle("nav-open");
                        setCollapseOpen(false);
                    }}
                />
            ) : null}
            <Navbar className={"fixed-top " + navbarColor} color="info" expand="lg">
                <Container>
                    <UncontrolledDropdown className="button-dropdown">
                        <DropdownToggle
                            caret
                            data-toggle="dropdown"
                            href="#pablo"
                            id="navbarDropdown"
                            tag="a"
                            onClick={e => e.preventDefault()}
                        >
                            <span className="button-bar"></span>
                            <span className="button-bar"></span>
                            <span className="button-bar"></span>
                        </DropdownToggle>
                        <DropdownMenu aria-labelledby="navbarDropdown">
                            <DropdownItem header tag="a">
                                DØR-Til-DØR
              </DropdownItem>
                            <DropdownItem href="#pablo" onClick={e => e.preventDefault()}>
                                Mine reiser
              </DropdownItem>
                        </DropdownMenu>
                    </UncontrolledDropdown>
                    <div className="navbar-translate">
                        <NavbarBrand
                            href="https://vy.no"
                            target="_blank"
                            id="navbar-brand"
                        >
                            Vy Dør-til-Dør
            </NavbarBrand>
                        <UncontrolledTooltip target="#navbar-brand">
                            Designed by VySmidig Group 2019
            </UncontrolledTooltip>
                        <button
                            className="navbar-toggler navbar-toggler"
                            onClick={() => {
                                document.documentElement.classList.toggle("nav-open");
                                setCollapseOpen(!collapseOpen);
                            }}
                            aria-expanded={collapseOpen}
                            type="button"
                        >
                            <span className="navbar-toggler-bar top-bar"></span>
                            <span className="navbar-toggler-bar middle-bar"></span>
                            <span className="navbar-toggler-bar bottom-bar"></span>
                        </button>
                    </div>
                    <Collapse
                        className="justify-content-end"
                        isOpen={collapseOpen}
                        navbar
                    >
                        <Nav navbar>
                            <NavItem>
                                <NavLink href="https://github.com/olealgoritme/vy-group-project">
                                    Sjekk ut vår GitHub Repo
                </NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink
                                    href="https://twitter.com/vy-group"
                                    target="_blank"
                                    id="twitter-tooltip"
                                >
                                    <i className="fab fa-twitter"></i>
                                    <p className="d-lg-none d-xl-none">Twitter</p>
                                </NavLink>
                                <UncontrolledTooltip target="#twitter-tooltip">
                                    Følg oss på Twitter
                </UncontrolledTooltip>
                            </NavItem>
                            <NavItem>
                                <NavLink
                                    href="https://www.facebook.com/vy-group"
                                    target="_blank"
                                    id="facebook-tooltip"
                                >
                                    <i className="fab fa-facebook-square"></i>
                                    <p className="d-lg-none d-xl-none">Facebook</p>
                                </NavLink>
                                <UncontrolledTooltip target="#facebook-tooltip">
                                    Treff oss på Facebook
                </UncontrolledTooltip>
                            </NavItem>
                            <NavItem>
                                <NavLink
                                    href="https://www.instagram.com/selectfromgrr"
                                    target="_blank"
                                    id="instagram-tooltip"
                                >
                                    <i className="fab fa-instagram"></i>
                                    <p className="d-lg-none d-xl-none">Instagram</p>
                                </NavLink>
                                <UncontrolledTooltip target="#instagram-tooltip">
                                    Følg oss Instagram
                </UncontrolledTooltip>
                            </NavItem>
                        </Nav>
                    </Collapse>
                </Container>
            </Navbar>
        </>
    );
}

export default ExamplesNavbar;
