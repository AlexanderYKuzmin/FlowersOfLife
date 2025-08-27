package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.usecases.auth.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.test_utils.MainDispatcherRule
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlowImpl
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SignInUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class AuthLoginViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var uiEventFlow: UiEventFlowImpl
    private lateinit var viewModel: AuthLoginViewModel

    private val signInUseCase = mockk<SignInUseCase>()
    private val getUserFromLocalStorageUseCase = mockk<GetUserFromLocalStorageUseCase>()
    private val navigationManager = mockk<NavigationManager>(relaxed = true)

    @Before
    fun setup() {
        uiEventFlow = UiEventFlowImpl()
        coEvery { getUserFromLocalStorageUseCase() } returns User()

        /*viewModel = AuthLoginViewModel(
            signInUseCase = signInUseCase,
            getUserFromLocalStorageUseCase = getUserFromLocalStorageUseCase,
            navigationManager = navigationManager,
            uiEventFlow = uiEventFlow
        )*/
    }

    /*@Test
    fun `signInUser with invalid credentials sets AuthState Error`() = runTest {
        *//*coEvery { signInUseCase(any()) } throws IllegalLoginException()

        viewModel.signInUser(
            AuthCredentials(email = "test@test.com", password = "123456"),
            rememberMe = false
        )

        advanceUntilIdle()

        val authState = viewModel.authState.value
        assertTrue(authState is AuthState.Error)
        assertTrue((authState as AuthState.Error).throwable is IllegalLoginException)*//*
    }*/

}
