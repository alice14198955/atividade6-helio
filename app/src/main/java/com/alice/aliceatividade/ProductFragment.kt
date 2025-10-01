package com.alice.aliceatividade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alice.aliceatividade.databinding.FragmentProductBinding
import com.alice.aliceatividade.model.Product
import com.alice.aliceatividade.model.ProductViewModel
import java.lang.NumberFormatException

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
            .create(ProductViewModel::class.java)

        setupObservers()
        setupListeners()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.products.observe(viewLifecycleOwner) { list ->
            val sb = StringBuilder()
            list.forEach { p ->
                sb.append("• ${p.name} — R$ ${String.format("%.2f", p.price)}\n")
            }
            binding.tvProductsList.text = if (sb.isEmpty()) "Nenhum produto cadastrado." else sb.toString()
        }
    }

    private fun setupListeners() {
        binding.btnAddProduct.setOnClickListener {
            val name = binding.etProductName.text.toString().trim()
            val priceStr = binding.etProductPrice.text.toString().trim()

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha nome e preço.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = try {
                priceStr.replace(",", ".").toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Preço inválido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(name = name, price = price)
            viewModel.insert(product)

            binding.etProductName.text?.clear()
            binding.etProductPrice.text?.clear()
            Toast.makeText(requireContext(), "Produto cadastrado.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
