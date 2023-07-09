import { Box, AppBar, Toolbar, IconButton, Typography, Button } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import SchoolIcon from "@mui/icons-material/School";
import LocalLibraryIcon from "@mui/icons-material/LocalLibrary";

export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;

	return (
		<Box sx={{ flexGrow: 1 }}>
			<AppBar position="static" sx={{ marginBottom: "20px" }}>
				<Toolbar>
					<IconButton
						component={Link}
						to="/"
						size="large"
						edge="start"
						color="inherit"
						aria-label="school"
						sx={{ mr: 2 }}>
						<SchoolIcon />
					</IconButton>
					<Typography variant="h6" component="div" sx={{ mr: 5 }}>
						Client management
					</Typography>
					<Button
						variant={path.startsWith("/client") ? "outlined" : "text"}
						to="/client"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						Client
					</Button>

					<Button
						variant={path.startsWith("/attorney") ? "outlined" : "text"}
						to="/attorney"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						Attorney
					</Button>

					<Button
						variant={path.startsWith("/lawsuit") ? "outlined" : "text"}
						to="/lawsuit"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						Lawsuits
					</Button>

					<Button
						variant={path.startsWith("/attorney-on-lawsuit") ? "outlined" : "text"}
						to="/attorney-on-lawsuit"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						Attorney On Lawsuits
					</Button>

					<Button
						variant={path.startsWith("/attorney-travel") ? "outlined" : "text"}
						to="/attorney-travel"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						Attorney Travel Statistic
					</Button>

					<Button
						variant={path.startsWith("/top-profits") ? "outlined" : "text"}
						to="/top-profits"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						Profit Statistic
					</Button>
				</Toolbar>
			</AppBar>
		</Box>
	);
};