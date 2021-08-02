import { render, screen } from '@testing-library/react';
import App from './App';
import Landing from './pages/Landing'

test('renders test', () => {
  render(<App />);
  const textElement = screen.getByText(/Loading/i);
  expect(textElement).toBeInTheDocument();
});

test('renders Landing', () => {
  render(<Landing />);
  const textElement = screen.getByText(/the landing page/i);
  expect(textElement).toBeInTheDocument();
});
